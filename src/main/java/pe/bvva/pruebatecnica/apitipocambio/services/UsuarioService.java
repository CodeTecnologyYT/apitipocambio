/*
 * @(#)UsuarioService.java
 *
 * Copyright (c) BBVA (PERU). All rights reserved.
 *
 * All rights to this product are owned by BVVA and may only
 * be used under the terms of its associated license document. You may NOT
 * copy, modify, sublicense, or distribute this source file or portions of
 * it unless previously authorized in writing by BBVA.
 * In any event, this notice and the above copyright must always be included
 * verbatim with this file.
 */
package pe.bvva.pruebatecnica.apitipocambio.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import pe.bvva.pruebatecnica.apitipocambio.converts.UsuarioConvert;
import pe.bvva.pruebatecnica.apitipocambio.exceptions.BadRequestException;
import pe.bvva.pruebatecnica.apitipocambio.models.entities.RolEntity;
import pe.bvva.pruebatecnica.apitipocambio.models.entities.UsuarioEntity;
import pe.bvva.pruebatecnica.apitipocambio.models.entities.UsuarioRoleEntity;
import pe.bvva.pruebatecnica.apitipocambio.models.requests.LoginRequest;
import pe.bvva.pruebatecnica.apitipocambio.models.responses.LoginResponse;
import pe.bvva.pruebatecnica.apitipocambio.repositories.RolRepository;
import pe.bvva.pruebatecnica.apitipocambio.repositories.UsuarioRepository;
import pe.bvva.pruebatecnica.apitipocambio.repositories.UsuarioRolRepository;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UsuarioService.
 *
 * @author Bryan Rosas Quispe.
 * @version 1.0.0, 28-04-2022
 */
@Service
@Slf4j
public class UsuarioService {

    @Value("${jwt.password}")
    private String jwtSecret;

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final RolRepository rolRepository;
    private final UsuarioConvert usuarioConvert;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,UsuarioRolRepository usuarioRolRepository,
        RolRepository rolRepository,UsuarioConvert usuarioConvert) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.rolRepository = rolRepository;
        this.usuarioConvert = usuarioConvert;
    }

    public Single<UsuarioEntity> createUser(String idRol, UsuarioEntity usuario) {
        return Single.create(singleSubscriber -> {
            Optional<UsuarioEntity> result = usuarioRepository.findByUsername(usuario.getUsername());

            if (result.isPresent()) {
                singleSubscriber.onError(new BadRequestException(String.format("Usuario con id [%s] ya existe!",
                    usuario.getUsername())));
            } else {
                String encoder = passwordEncoder.encode(usuario.getPassword());
                usuario.setPassword(encoder);
                usuario.setActivo(true);
                Optional<RolEntity> rolEntity = rolRepository.findById(idRol);
                if(rolEntity.isEmpty()){
                    singleSubscriber.onError(new BadRequestException("El rol no existe"));
                }else{
                    UsuarioEntity usuarioEntity = usuarioRepository.save(usuario);
                    UsuarioRoleEntity usuarioRole = UsuarioRoleEntity.builder()
                        .rol(rolEntity.get())
                        .usuario(usuarioEntity)
                        .build();
                    usuarioRolRepository.save(usuarioRole);
                    singleSubscriber.onSuccess(usuarioEntity);
                }
            }
        });
    }

    public Single<LoginResponse> login(LoginRequest request) {
        return Single.create(singleSubscriber -> {
            UsuarioEntity usuario =
                usuarioRepository.findByUsername(request.getUsername()).orElseGet(() -> {
                    singleSubscriber.onError(
                        new BadRequestException("El usuario o password no existe"));
                    return new UsuarioEntity();
                });

            if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
                singleSubscriber.onError(new BadRequestException("El usuario o password no existe"));
            }
            String token = createToken(usuario);
            singleSubscriber.onSuccess(LoginResponse.builder().usuario(usuarioConvert.fromEntity(usuario)).token(token).build());
        });
    }

    public String createToken(UsuarioEntity usuarioEntity) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (1000 * 60 * 60));
        return Jwts.builder()
            .setSubject(usuarioEntity.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException e) {
            log.error("Formato de jwt no validacion");
        } catch (MalformedJwtException e) {
            log.error("JWT no es correcto la construccion");
        } catch (SignatureException e) {
            log.error("Al verificar la firma no existe");
        } catch (ExpiredJwtException e) {
            log.error("JWT no existe");
        }
        return false;
    }

    public String getUsernameFromToken(String jwt) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException("Token invalido");
        }
    }

}