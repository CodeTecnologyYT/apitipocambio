CREATE TABLE public.tdc_rol (
    id varchar NULL,
    descripcion varchar NULL,
    CONSTRAINT tdc_rol_pk PRIMARY KEY (id)
);


CREATE TABLE public.tdc_usuario (
    id varchar NOT NULL,
    username varchar NULL,
    "password" varchar NULL,
    activo bool NULL DEFAULT true,
    CONSTRAINT tdc_usuario_pk PRIMARY KEY (id),
    CONSTRAINT tdc_usuario_un UNIQUE (username)
);

CREATE TABLE public.tdc_usuario_rol (
    id varchar NULL,
    id_rol varchar NULL,
    id_usuario varchar NULL,
    CONSTRAINT tdc_usuario_rol_fk FOREIGN KEY (id_usuario) REFERENCES public.tdc_usuario(id),
    CONSTRAINT tdc_usuario_rol_fk_1 FOREIGN KEY (id_rol) REFERENCES public.tdc_rol(id)
);


CREATE TABLE public.tdc_moneda (
   id varchar NULL,
   descripcion varchar NULL,
   CONSTRAINT tdc_moneda_pk PRIMARY KEY (id)
);

CREATE TABLE public.tdc_tipocambio (
   id varchar NULL,
   id_monedaentrada varchar NULL,
   id_monedasalida varchar NULL,
   valor decimal NULL DEFAULT 0,
   periodo date NULL DEFAULT CURRENT_DATE,
   activo boolean NULL DEFAULT TRUE,
   CONSTRAINT tdc_tipocambio_pk PRIMARY KEY (id),
   CONSTRAINT tdc_tipocambio_fk FOREIGN KEY (id_monedaentrada) REFERENCES public.tdc_moneda(id),
   CONSTRAINT tdc_tipocambio_fk_1 FOREIGN KEY (id_monedasalida) REFERENCES public.tdc_moneda(id)
);

CREATE TABLE public.tdc_tipocambio_audit (
     id varchar NOT NULL,
     id_monedaentrada varchar NULL,
     id_monedasalida varchar NULL,
     valor numeric NULL DEFAULT 0,
     periodo date NULL,
     activo bool NULL DEFAULT true,
     usuario_operacion varchar NULL,
     fecha_operacion timestamp NULL DEFAULT CURRENT_DATE,
     CONSTRAINT tdc_tipocambio_audit_pk PRIMARY KEY (id)
);






