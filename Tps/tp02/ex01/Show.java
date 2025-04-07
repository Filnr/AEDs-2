import java.util.*;
import java.text.*;
import java.io.*;

//simple date format e date
public class Show {
    private String show_ID;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private Date data_added;
    private int release_year;
    private String rating;
    private String duration;
    private String[] listed_in;

    public void setID(String id) {
        this.show_ID = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setCast(String[] cast) {
        this.cast = cast.clone();
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDate(Date data_added) {
        this.data_added = data_added;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setListed(String[] listed_in) {
        this.listed_in = listed_in.clone();
    }

    public void setRelease(int year) {
        this.release_year = year;
    }

    public String getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDirector() {
        return this.director;
    }

    public String[] getCast() {
        return this.cast;
    }

    public String getCountry(){
        return this.country;
    }

    public Date getData_added() {
        return this.data_added;
    }

    public String getDuration() {
        return this.duration;
    }

    public String[] getListed_in() {
        return listed_in;
    }

    public String getRating() {
        return rating;
    }

    public String getShow_ID() {
        return show_ID;
    }

    public Show() {
        show_ID = "";
        type = "";
        title = "";
        director = "";
        cast = new String[0];
        country = "";
        data_added = null;
        release_year = 0;
        rating = "";
        duration = "";
        listed_in = new String[0];
    }

    public Show(int id) {
        String caminho = "temp/disneyplus.csv";
        String linha = "";
        int contador = 0;
        boolean encontrado = false;
        try (BufferedReader ler = new BufferedReader(new FileReader(caminho))) {
            while ((linha = ler.readLine()) != null && !encontrado) {
                if (contador == id) {
                    ler(linha);
                    encontrado = true;
                }
                contador++;
            }
        } catch (IOException erro) {
            erro.printStackTrace();
        }
    }

    private int contaAtores(String linha, int posicao) {
        // Função resposavel por ler quantos conjuntos de palavras tem por meio da
        // virgula
        // É usada para ler a quantidade de atores e categorias
        int qtAtores = 1;
        while (posicao < linha.length() && linha.charAt(posicao) != '"') {
            if (linha.charAt(posicao) == ',') {
                qtAtores++;
            }
            posicao++;
        }
        return qtAtores;
    }

    public void ler(String linha) {
        int pos = 0;
        // le a entrada enquanto não ler o ',' e depois pula o ','
        String ID = "";
        while (pos < linha.length() && linha.charAt(pos) != ',') {
            ID += linha.charAt(pos);
            pos++;
        }
        setID(ID);
        pos++;
        // Se a primeira letra lida for M, o tipo será Movie e senão TV
        String tipo = "";
        if (pos < linha.length() && linha.charAt(pos) == 'M') {
            tipo = "Movie";
            pos += 6;
        } else {
            tipo = "TV Show";
            pos += 8;
        }
        setType(tipo);
        String titulo = "";
        while (pos < linha.length() && linha.charAt(pos) != ',') {
            titulo += linha.charAt(pos);
            pos++;
        }
        setTitle(titulo);
        pos++;
        // leitura dos diretores
        String diretor = "";
        if (pos < linha.length() && linha.charAt(pos) != ',') {
            if (linha.charAt(pos) == '"') {
                pos++;
                while (pos < linha.length() && linha.charAt(pos) != '"') {
                    diretor += linha.charAt(pos);
                    pos++;
                }
                pos += 2;
            } else {
                while (pos < linha.length() && linha.charAt(pos) != ',') {
                    diretor += linha.charAt(pos);
                    pos++;
                }
                pos++;
            }
        } else {
            pos++;
        }
        setDirector(diretor);
        // Elenco
        String[] elenco;
        if (pos < linha.length() && linha.charAt(pos) != ',') {
            if (linha.charAt(pos) == '"') {
                pos++;
                elenco = new String[contaAtores(linha, pos)];
                int ator = 0;
                for (int i = 0; i < elenco.length; i++)
                    elenco[i] = "";
                while (pos < linha.length() && linha.charAt(pos) != '"') {
                    while (pos < linha.length() && linha.charAt(pos) != ',' && linha.charAt(pos) != '"') {
                        elenco[ator] += linha.charAt(pos);
                        pos++;
                    }
                    if (pos < linha.length() && linha.charAt(pos) == ',') {
                        pos++;
                        ator++;
                    }
                }
                if (pos < linha.length())
                    pos += 2;
            } else {
                elenco = new String[1];
                elenco[0] = "";
                while (pos < linha.length() && linha.charAt(pos) != ',') {
                    elenco[0] += linha.charAt(pos);
                    pos++;
                }
                if (pos < linha.length())
                    pos++;
            }
        } else {
            elenco = new String[0];
            pos++;
        }
        setCast(elenco);
        // País
        String pais = "";
        if (linha.charAt(pos) != ',') {
            while (linha.charAt(pos) != ',') {
                pais += linha.charAt(pos);
                pos++;
            }
            pos++;
        } else {
            pos++;
        }
        setCountry(pais);
        // data de adição
        String data = "";
        if (linha.charAt(pos) == '"') {
            pos++;
            while (linha.charAt(pos) != '"') {
                data += linha.charAt(pos);
                pos++;
            }
            pos += 2;
            SimpleDateFormat formataEntrada = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
            try {
                setDate(formataEntrada.parse(data));
            } catch (ParseException erro) {
                erro.printStackTrace();
            }
        } else {
            pos++;
        }
        // ano de lançamento
        int ano = 0;
        if (linha.charAt(pos) != ',') {
            int decimal = 1000;
            while (linha.charAt(pos) != ',') {
                int numero = linha.charAt(pos) - '0';
                ano += numero * decimal;
                pos++;
                decimal /= 10;
            }
            pos++;
        } else {
            pos++;
        }
        setRelease(ano);
        // Avaliação
        String rate = "";
        while (linha.charAt(pos) != ',') {
            rate += linha.charAt(pos);
            pos++;
        }
        pos++;
        setRating(rate);
        // Duração
        String duracao = "";
        while (linha.charAt(pos) != ',') {
            duracao += linha.charAt(pos);
            pos++;
        }
        pos++;
        setDuration(duracao);
        // Categorias
        String[] categorias;
        if (linha.charAt(pos) != '"') {
            categorias = new String[1];
            categorias[0] = "";
            while (pos < linha.length() && linha.charAt(pos) != ',') {
                categorias[0] += linha.charAt(pos);
                pos++;
            }

        } else {
            pos++;
            categorias = new String[contaAtores(linha, pos)];
            int categoria = 0;
            for (int i = 0; i < categorias.length; i++)
                categorias[i] = "";
            while (pos < linha.length() && linha.charAt(pos) != '"' && categoria < categorias.length) {
                while (pos < linha.length() && linha.charAt(pos) != ',') {
                    categorias[categoria] += linha.charAt(pos);
                    pos++;
                }
                pos++;
                categoria++;
            }
        }
        setListed(categorias);
    }

    public Show(String entrada) {
        ler(entrada);
    }

    public Show clone(){
        Show clonado = new Show();
        clonado.show_ID = this.show_ID;
        clonado.cast = this.cast.clone();
        clonado.country = this.country;
        clonado.data_added = this.data_added;
        clonado.director = this.director;
        clonado.duration = this.duration;
        clonado.rating = this.rating;
        clonado.release_year = this.release_year;
        clonado.type = this.type;
        clonado.listed_in = this.listed_in.clone();
        return clonado;
    }

    public void imprimir() {
        System.out.println(show_ID);
        System.out.println(type);
        System.out.println(title);
        System.out.println(director);
        System.out.println(Arrays.toString(cast));
        System.out.println(country);
        System.out.println(data_added);
        System.out.println(release_year);
        System.out.println(rating);
        System.out.println(duration);
        System.out.println(Arrays.toString(listed_in));
    }
}
