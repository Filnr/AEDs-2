import java.util.*;
import java.text.*;
import java.io.*;

//simple date format e date
class Show {
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

    public String getCountry() {
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

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

    public Show() {
        show_ID = "NaN";
        type = "NaN";
        title = "NaN";
        director = "NaN";
        cast = null;
        country = "NaN";
        data_added = null;
        release_year = 0;
        rating = "NaN";
        duration = "NaN";
        listed_in = null;
    }

    public Show(int id) {
        // Construtor responsavel por ler a linha exata do id lido, e chamar a função
        // responsavel pela leitura e atribuição
        String caminho = "/tmp/disneyplus.csv";
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

    private int extraiId(String entrada) {
        // Converte a entrada que esta em formato de string, para um valor int que será
        // usado como chave para ler o csv
        int valor = 0;
        int multiplicador = 1;
        for (int i = entrada.length() - 1; i > 0; i--) {
            int numero = entrada.charAt(i) - '0';
            valor += numero * multiplicador;
            multiplicador *= 10;
        }
        return valor;
    }

    public Show(String entrada) {
        // Construtor responsavel por ler a linha exata do id lido, e chamar a função
        // responsavel pela leitura e atribuição
        String caminho = "./tmp/disneyplus.csv";
        int id = extraiId(entrada);
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

    public void ler(String linha) {
        // Inicaliza uma lista de Strings que contera cada campo da linha
        List<String> listaCampos = new ArrayList<>();
        StringBuilder campoAtual = new StringBuilder();
        boolean temAspas = false;
        for (int i = 0; i < linha.length(); i++) {
            char letra = linha.charAt(i);
            // Estrutura responsavel por interpretar o inicio e fim de cada campo, e
            // adicionar as letras
            if (letra == '"') {
                temAspas = !temAspas;
            } else if (letra == ',' && !temAspas) {
                listaCampos.add(campoAtual.toString());
                campoAtual.setLength(0);
            } else {
                campoAtual.append(letra);
            }
        }
        listaCampos.add(campoAtual.toString());
        // Adição dos campos aos seus respectivos atributos
        String[] campos = new String[listaCampos.size()];
        campos = listaCampos.toArray(campos);
        this.show_ID = campos[0];
        this.type = campos[1].trim().equalsIgnoreCase("movie") ? "Movie" : "TV Show";
        this.title = campos[2];
        this.director = campos[3].equals("") ? "NaN" : campos[3];
        this.cast = campos[4].equals("") ? new String[] { "NaN" } : campos[4].split(", ");
        if (this.cast.length > 1) {
            Ordena(this.cast);
        }
        this.country = campos[5].equals("") ? "NaN" : campos[5];
        try {
            this.data_added = campos[6].equals("") ? null : dateFormat.parse(campos[6]);
        } catch (Exception e) {
            this.data_added = null;
        }
        this.release_year = campos[7].equals("") ? 0 : Integer.parseInt(campos[7]);
        this.rating = campos[8];
        this.duration = campos[9];
        this.listed_in = campos[10].equals("") ? new String[] { "NaN" } : campos[10].split(", ");
        if (this.listed_in.length > 1) {
            Ordena(this.listed_in);
        }
    }

    public Show clone() {
        // Metodo responsavel por criar um clone do objeto selecionado
        Show clonado = new Show();
        clonado.show_ID = this.show_ID;
        clonado.type = this.type;
        clonado.title = this.title;
        clonado.director = this.director;
        clonado.cast = this.cast.clone();
        clonado.country = this.country;
        clonado.data_added = this.data_added;
        clonado.duration = this.duration;
        clonado.rating = this.rating;
        clonado.release_year = this.release_year;
        clonado.listed_in = this.listed_in.clone();
        return clonado;
    }

    public void Ordena(String[] vetor) {
        // Função responsavel por ordenar em ordem alfabetica um vetor de strings
        // Ordenção por seleção
        for (int i = 0; i < vetor.length; i++) {
            int indiceMin = i;
            for (int j = (i + 1); j < vetor.length; j++) {
                if (vetor[j].compareTo(vetor[indiceMin]) < 0) {
                    indiceMin = j;
                }
            }
            if (indiceMin != i) {
                String temp = vetor[i];
                vetor[i] = vetor[indiceMin];
                vetor[indiceMin] = temp;
            }
        }
    }

    public void imprimir() {
        // antes de começar a impressão, ordena os vetores que precisam ser ordenados
        SimpleDateFormat data = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        String dataFormatada;
        if (getData_added() != null) {
            dataFormatada = data.format(getData_added());
        } else {
            dataFormatada = "NaN";
        }
        System.out.printf("=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##\n",
                show_ID, title, type, director, Arrays.toString(cast), country, dataFormatada, release_year,
                rating, duration, Arrays.toString(listed_in));
    }
}

public class Lista {
    private Show[] lista;
    private int tam;

    public Lista() {
        lista = new Show[500];
        tam = 0;
    }

    public void inserirFim(Show show) throws Exception {
        // Tratamento de excessão para caso a posição ou o tamanho saia do tamanho do
        // array
        if (tam >= lista.length)
            throw new Exception();
        // Insere o show diretamente na ultima posição e em seguida incrementa tam
        lista[tam++] = show;
    }

    public void inserirInicio(Show show) throws Exception {
        // Tratamento de excessão para caso a posição ou o tamanho saia do tamanho do
        // array
        if (tam >= lista.length)
            throw new Exception();
        // Abre espaço no array para ser colocado um elemento no inicio
        for (int i = tam; i > 0; i--) {
            lista[i] = lista[i - 1];
        }
        lista[0] = show;
        tam++;
    }

    public void inserir(Show show, int pos) throws Exception {
        // Tratamento de excessão para caso a posição ou o tamanho saia do tamanho do
        // array
        if (tam >= lista.length || pos > tam || pos < 0)
            throw new Exception();
        // Abre espaço na posição para ser inserido o show
        for (int i = pos; i > 0; i--) {
            lista[i] = lista[i - 1];
        }
        lista[pos] = show;
        tam++;
    }

    public Show removerInicio() {
        // Armazena o elemento a ser removido
        Show removido = lista[0];
        // Disloca os elementos para esquerda, para apagar o inicio
        for (int i = 0; i < tam - 1; i++) {
            lista[i] = lista[i + 1];
        }
        tam--;
        return removido;
    }

    public Show remover(int pos) throws Exception {
        System.out.println(lista[pos]);
        if (pos >= tam)
            throw new Exception();
        // Armaza o show removido
        Show removido = lista[pos];
        // Disloca os elementos para ocupar o espaço
        for (int i = pos; i < tam - 1; i++) {
            lista[i] = lista[i + 1];
        }
        tam--;
        return removido;
    }

    public Show removerFim() throws Exception {
        System.out.println(lista[tam-1]);
        if (tam == 0)
            throw new Exception();
        tam--;
        Show removido = lista[tam];
        lista[tam] = null;
        return removido;
    }

    public static boolean ehRemovePos(String comando) {
        return comando.equals("R*");
    }

    public static boolean ehInsere(String comando) {
        return (comando.equals("II") || comando.equals("I*") || comando.equals("IF"));
    }

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in, "UTF-8");
        String entrada = ler.nextLine();
        Lista lista = new Lista();
        Show show;
        ArrayList<String> removidos = new ArrayList<>();
        try {
            while (!entrada.equals("FIM")) {
                show = new Show(entrada);
                lista.inserirFim(show);
                entrada = ler.nextLine();
            }
            int n = Integer.parseInt(ler.nextLine());
            for (int i = 0; i < n; i++) {
                entrada = ler.nextLine();
                String[] partes = entrada.split(" ");
                String comando = partes[0];
                String id = "1";
                int pos = 0;
                if (ehInsere(comando)) {
                    if (partes.length == 3) {
                        pos = Integer.parseInt(partes[1]);
                        id = partes[2];
                    } else {
                        id = partes[1];
                    }
                } else if (ehRemovePos(comando)) {
                    pos = Integer.parseInt(partes[1]);
                }
                switch (comando) {
                    case "II":
                        show = new Show(id);
                        lista.inserirInicio(show);
                        break;
                    case "I*":
                        show = new Show(id);
                        lista.inserir(show, pos);
                        break;
                    case "IF":
                        show = new Show(id);
                        lista.inserirFim(show);
                        break;
                    case "RI":
                        removidos.add(lista.removerInicio().getTitle());
                        break;
                    case "R*":
                        removidos.add(lista.remover(pos).getTitle());
                        break;
                    case "RF":
                        removidos.add(lista.removerFim().getTitle());
                }
            }
            for (String title : removidos) {
                System.out.println("(R)" + title);
            }
            for (int i = 0; i < lista.tam; i++) {
                lista.lista[i].imprimir();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        ler.close();
    }
}