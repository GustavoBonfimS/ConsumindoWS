package modelo;

import android.app.DownloadManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.ContentHandler;
import java.sql.Date;
import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class RetrofitConfig {
    private final Retrofit retrofit;
    String url = "http://10.10.54.47:8080/WigWS/webresources/";

    Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();
    }

    public WigService getWigService() {
        return this.retrofit.create(WigService.class);
    }

    public interface WigService {

        //---------------------------------Usuario-----------------------------------

        @GET("usuario/get/{login}")
        Call<Usuario> buscarUsuario(@Path("login") String login); // admin

        @GET("usuario/login/{login}/{senha}")
        Call<String> validarLogin(@Path("login") String login, @Path("senha") String senha);

        @GET("usuario/Listar")
        Call<Usuario> listarUsuario(); // não precisa // admin

        @DELETE("usuario/Excluir/{login}")
        Call<Usuario> excluirUsuario(@Path("login") String login); // admin

        @POST("usuario/Cadastrar")
        @Headers("Content-Type: application/json")
        Call<Usuario> cadastrarUsuario(@Body Usuario usuario); // admin

        @PUT("usuario/Alterar") //admin
        Call<Usuario> alterarUsuario(@Body Usuario usuario);


        //---------------------------------Cliente-----------------------------------

        @GET("cliente/get/{login}")
        @Headers("Content-Type: application/json")
        Call<Cliente> getCliente(@Path("login") String login);

        @POST("cliente/Cadastrar")
        @Headers("Content-Type: application/json")
        Call<Cliente> cadastrarCliente(@Body Cliente cliente);

        @GET("cliente/Listar")
        @Headers("Content-Type: application/json")
        Call<List<Cliente>> listarCliente();

        @PUT("cliente/Alterar")
        @Headers("Content-Type: application/json")
        Call<Cliente> alterar (@Body Cliente cliente);

        @GET("cliente/atualizarIndex/{login}")
        @Headers("Content-Type: application/json")
        Call<List<Avaliacao>> atualizarIndex (@Path("login") String login);

        //---------------------------------Avaliação------------------------------------------

        @GET("cliente/Avaliacao/minhas/{idcliente}")
        Call<List<Avaliacao>> minhasAvaliacoes(@Path("idcliente") int idcliente);

        @GET("cliente/Avaliacao/Listar")
        Call<List<Avaliacao>> listarAvaliacao();

        @GET("cliente/Avaliacao/Listar/{idempresa}")
        @Headers("Content-Type: application/json")
        Call<List<Avaliacao>> listarAvaliacaoDaEmpresa(int idempresa);


        @POST("cliente/Avaliacao/Inserir")
        @Headers("Content-Type: application/json")
        Call<Avaliacao> inserirAvaliacao(@Body Avaliacao avaliacao);

        @POST("cliente/Avaliacao/Responder")
        @Headers("Content-Type: application/json")
        Call<Avaliacao> responderAvaliacao(@Body Avaliacao avaliacao);

        @GET("cliente/Avaliacao/get/{conteudo}")
        @Headers("Content-Type: application/json")
        Call<Avaliacao> getAvaliacao(@Path("conteudo") String conteudo);

        //---------------------------------Empresa------------------------------------------

        @POST("empresa/Inserir")
        @Headers("Content-Type: application/json")
        Call<Empresa> inserirEmpresa(@Body Empresa empresa);

        @GET("empresa/get/{CNPJ}")
        @Headers("Content-Type: application/json")
        Call<Empresa> getEmpresa(@Path("CNPJ") String cnpj);

        @GET("empresa/get/id/{idempresa}")
        @Headers("Content-Type: application/json")
        Call<Empresa> getEmpresaPeloID(@Path("idempresa") int idempresa);

        @GET("empresa/get/nome/{nome}")
        @Headers("Content-Type: application/json")
        Call<Empresa> getEmpresaPeloNome(@Path("nome") String nome);

        @GET("empresa/Listar")
        @Headers("Content-Type: application/json")
        Call<List<Empresa>> listarEmpresas();

        @GET("empresa/pesquisa/{empresa}")
        @Headers("Content-Type: application/json")
        Call<List<Empresa>> pesquisa(@Path("empresa") String empresa);

    }


}
