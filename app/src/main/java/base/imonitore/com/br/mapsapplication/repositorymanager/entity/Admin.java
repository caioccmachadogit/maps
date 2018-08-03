package base.imonitore.com.br.mapsapplication.repositorymanager.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ccouto on 22/03/2018.
 */
@Entity(tableName = "ADMIN_CFC")
public class Admin implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Long idPessoa;

    private String nomePessoa;

    private String cpfPessoa;

    private String token;

    private String login;

    private String senha;

    private String codigoEmpresa;

    private Date expiracao;

    private Date dataSincronismoAgenda;

    public Admin() {
    }

    @Ignore
    public Admin(Long idPessoa, String nomePessoa, String cpfPessoa, String token, String login, String senha, String codigoEmpresa, Date expiracao) {
        this.idPessoa = idPessoa;
        this.nomePessoa = nomePessoa;
        this.cpfPessoa = cpfPessoa;
        this.token = token;
        this.login = login;
        this.senha = senha;
        this.codigoEmpresa = codigoEmpresa;
        this.expiracao = expiracao;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getCpfPessoa() {
        return cpfPessoa;
    }

    public void setCpfPessoa(String cpfPessoa) {
        this.cpfPessoa = cpfPessoa;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public Date getExpiracao() {
        return expiracao;
    }

    public void setExpiracao(Date expiracao) {
        this.expiracao = expiracao;
    }

    public Date getDataSincronismoAgenda() {
        return dataSincronismoAgenda;
    }

    public void setDataSincronismoAgenda(Date dataSincronismoAgenda) {
        this.dataSincronismoAgenda = dataSincronismoAgenda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
