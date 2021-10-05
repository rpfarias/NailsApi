package com.nail.domain.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/* @Data gera todos os clichês normalmente associados a um POJO simples
 (Plain Old Java Object): getters para todos os campos, setters para todos os campos não finais
 e implementações toString , equals e hashCode apropriadas e um construtor. */
@Data
@Entity(name = "tbl_client")
public class Client implements Serializable {
    /*A serialização é quando um objeto é transformado, em uma cadeia de bytes e desta forma pode ser manipulado
     de maneira mais fácil, seja através de transporte pela rede ou salvo no disco. Após a transmissão ou
      o armazenamento esta cadeia de bytes pode ser transformada novamente no objeto Java que o originou.*/
    private static final long serialVersionUID = 1L;

    @Id //estou indicando que entidade Client, pussui como chave primária o campo Id.
    //@GeneratedValue é utilizada para informar que a geração do valor do identificador único da entidade
    // será gerenciada pelo provedor de persistência.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthDate;

    @Column(length = 11, unique = true)
    private String phone;

    //@Column é utilizada para especificar os detalhes da coluna que um campo ou propriedade será mapeado.
    //Principais atributos de @Column (name, length, nullable)
    @Column(length = 11, unique = true)
    private String cpf;

    @ToString.Exclude // estou excluindo esse campo do método ToString (a nível de campo)
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY) // um cliente para vários pedidos
    private List<Order> orders;
    /* fetch = FetchType.LAZY - significa que ao realizarmos um “SELECT * from Order”
    teremos todos os campos retornados, mas os campos com a propriedade FetchType.LAZY estarão nulos,
    mesmo que eles existam no banco. Essa é uma forma de não sobrecarregar a aplicação com dados inúteis
    que não serão utilizados, tornando-a rápida e performática. */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private User user;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)//um cliente para muitos tipos de serviços
    private List<KindOfService> kindOfServices;

}
