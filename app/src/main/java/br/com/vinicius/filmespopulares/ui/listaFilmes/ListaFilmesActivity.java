package br.com.vinicius.filmespopulares.ui.listaFilmes;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.vinicius.filmespopulares.R;
import br.com.vinicius.filmespopulares.data.mapper.FilmeMapper;
import br.com.vinicius.filmespopulares.data.model.Filme;
import br.com.vinicius.filmespopulares.data.network.ApiService;
import br.com.vinicius.filmespopulares.data.network.response.FilmesResult;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaFilmesActivity extends AppCompatActivity {

    private RecyclerView recyclerFilmes;
    private ListaFilmesAdapter filmesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filmes);

        configuraToolbar();
        configuraAdapter();
        obtemFilmes();



    }

    private void configuraToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void configuraAdapter(){
        recyclerFilmes = findViewById(R.id.recyclerView_filmes);

        filmesAdapter = new ListaFilmesAdapter();

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerFilmes.setLayoutManager(gridLayoutManager);
        recyclerFilmes.setAdapter(filmesAdapter);

    }
    private void obtemFilmes() {
        ApiService.getInstance()
                .obterFilmesPopulares("ddef6d95ebef5dc5af4334e9f6211d1c")
                .enqueue(new Callback<FilmesResult>() {
                    @Override
                    public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                        if (response.isSuccessful()) {
                            final List<Filme> listaFilmes = FilmeMapper.deResponseParaDominio(response.body().getResultadoFilmes());
                            filmesAdapter.setFilmes(listaFilmes);


                        }else {
                            mostraErro();
                        }
                    }

                    @Override
                    public void onFailure(Call<FilmesResult> call, Throwable t) {
                        mostraErro();

                    }
                });


    }
    private void mostraErro(){
        Toast.makeText(this, "Erro ao obter lista de filmes", Toast.LENGTH_SHORT).show();

    }

}
