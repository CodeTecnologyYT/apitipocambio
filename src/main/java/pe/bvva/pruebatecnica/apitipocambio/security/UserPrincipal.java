package pe.bvva.pruebatecnica.apitipocambio.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pe.bvva.pruebatecnica.apitipocambio.models.entities.UsuarioEntity;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private UsuarioEntity usuarioEntity;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(UsuarioEntity usuarioEntity){
        List<GrantedAuthority> authorities = usuarioEntity.getUsuarioRole().stream()
                .map(userRoleEntity -> new SimpleGrantedAuthority(userRoleEntity.getRol().getDescripcion())).collect(Collectors.toList());
        return new UserPrincipal(usuarioEntity,authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuarioEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return usuarioEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
