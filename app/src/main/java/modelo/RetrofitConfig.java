package modelo;

import java.net.ContentHandler;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class RetrofitConfig {
    private final Retrofit retrofit;
    String url = "http://10.10.54.164:8080/WigWS/webresources/";

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public WigService getWigService() {
        return this.retrofit.create(WigService.class);
    }

    public interface WigService {

        @GET("usuario/get/{login}")
        Call<Usuario> buscarUsuario(@Path("login") String login);

        @GET("usuario/login/{login}/{senha}")
        Call<String> validarLogin(@Path("login") String login, @Path("senha") String senha);

        @GET("usuario/Listar")
        Call<Usuario> listarUsuario();

        @DELETE("usuario/Excluir/{login}")
        Call<Usuario> excluirUsuario(@Path("login") String login);

        @POST("usuario/Cadastrar")
        Call<Usuario> cadastrarUsuario(String content);

        @PUT("usuario/Alterar")
        Call<Usuario> alterarUsuario(@Path("login") String login, @Path("senha") String senha);


        //---------------------------------Cliente-----------------------------------
        @POST("cliente/Cadastrar")
        Call<Cliente> cadastrarCliente();

        @GET("cliente/Listar")
        Call<Cliente> listarCliente();

        //---------------------------------Avaliação------------------------------------------
        @GET("cliente/Avaliacao/Listar")
        Call<Avaliacao> listarAvaliacao();

        @POST("cliente/Avaliacao/Inserir")
        Call<Avaliacao> inserirAvaliacao();

        @POST("cliente/Avaliacao/Responder")
        Call<Avaliacao> responderAvaliacao(String content);
    }


}
