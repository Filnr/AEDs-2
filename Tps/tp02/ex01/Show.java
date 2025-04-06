import java.util.*;
import java.text.*;
import java.io.*;

//simple date format e date
public class Show {
    private String show_ID;
    String type;
    String title;
    String director;
    String[] cast;
    String country;
    Date data_added;
    int release_year;
    String rating;
    String duration;
    String[] listed_in;

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
                    leDados(linha);
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

    public void leDados(String linha) {
        int pos = 0;
        // le a entrada enquanto não ler o ',' e depois pula o ','
        show_ID = "";
        while (pos < linha.length() && linha.charAt(pos) != ',') {
            show_ID += linha.charAt(pos);
            pos++;
        }
        pos++;
        // Se a primeira letra lida for M, o tipo será Movie e senão TV
        if (pos < linha.length() && linha.charAt(pos) == 'M') {
            type = "Movie";
            pos += 6;
        } else {
            type = "TV Show";
            pos += 8;
        }

        title = "";
        while (pos < linha.length() && linha.charAt(pos) != ',') {
            title += linha.charAt(pos);
            pos++;
        }
        pos++;
        // leitura dos diretores
        director = "";
        if (pos < linha.length() && linha.charAt(pos) != ',') {
            if (linha.charAt(pos) == '"') {
                pos++;
                while (pos < linha.length() && linha.charAt(pos) != '"') {
                    director += linha.charAt(pos);
                    pos++;
                }
                pos += 2;
            } else {
                while (pos < linha.length() && linha.charAt(pos) != ',') {
                    director += linha.charAt(pos);
                    pos++;
                }
                pos++;
            }
        } else {
            pos++;
        }
        // Elenco
        if (pos < linha.length() && linha.charAt(pos) != ',') {
            if (linha.charAt(pos) == '"') {
                pos++;
                cast = new String[contaAtores(linha, pos)];
                int ator = 0;
                for (int i = 0; i < cast.length; i++)
                    cast[i] = "";
                while (pos < linha.length() && linha.charAt(pos) != '"') {
                    while (pos < linha.length() && linha.charAt(pos) != ',' && linha.charAt(pos) != '"') {
                        cast[ator] += linha.charAt(pos);
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
                cast = new String[1];
                cast[0] = "";
                while (pos < linha.length() && linha.charAt(pos) != ',') {
                    cast[0] += linha.charAt(pos);
                    pos++;
                }
                if (pos < linha.length())
                    pos++;
            }
        } else {
            cast = new String[0];
            pos++;
        }
        // País
        country = "";
        if (linha.charAt(pos) != ',') {
            while (linha.charAt(pos) != ',') {
                country += linha.charAt(pos);
                pos++;
            }
            pos++;
        } else {
            pos++;
        }
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
                data_added = formataEntrada.parse(data);
            } catch (ParseException erro) {
                erro.printStackTrace();
            }
        } else {
            pos++;
        }
        // ano de lançamento
        if (linha.charAt(pos) != ',') {
            int decimal = 1000;
            while (linha.charAt(pos) != ',') {
                int numero = linha.charAt(pos) - '0';
                release_year += numero * decimal;
                pos++;
                decimal /= 10;
            }
            pos++;
        } else {
            release_year = 0;
            pos++;
        }
        // Avaliação
        rating = "";
        while (linha.charAt(pos) != ',') {
            rating += linha.charAt(pos);
            pos++;
        }
        pos++;

        // Duração
        duration = "";
        while (linha.charAt(pos) != ',') {
            duration += linha.charAt(pos);
            pos++;
        }
        pos++;

        // Categorias
        if (linha.charAt(pos) != '"') {
            listed_in = new String[1];
            listed_in[0] = "";
            while (pos < linha.length() && linha.charAt(pos) != ',') {
                listed_in[0] += linha.charAt(pos);
                pos++;
            }

        } else {
            pos++;
            listed_in = new String[contaAtores(linha, pos)];
            int categoria = 0;
            for (int i = 0; i < listed_in.length; i++)
                listed_in[i] = "";
            while (pos < linha.length() && linha.charAt(pos) != '"' && categoria < listed_in.length) {
                while (pos < linha.length() && linha.charAt(pos) != ',') {
                    listed_in[categoria] += linha.charAt(pos);
                    pos++;
                }
                pos++;
                categoria++;
            }
        }
    }

    public Show(String entrada) {
        leDados(entrada);
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
