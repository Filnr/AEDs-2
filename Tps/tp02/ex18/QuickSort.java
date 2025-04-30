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
        String caminho = "tmp/disneyplus.csv";
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

    public Show(String entrada) {
        // Construtor que constroi o objeto a partir da linha do csv
        ler(entrada);
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

public class QuickSort {
    public static boolean ehFim(String entrada) {
        // Função responsavel por verificar se a entrada recebida é FIM
        Boolean fim = false;
        if (entrada.length() == 3) {
            if (entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M') {
                fim = true;
            }
        }
        return fim;
    }

    public static int converteStr(String entrada) {
        // COnverte a entrada que esta em formato de string, para um valor int que será
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

    public static int compararPorDataTitulo(Show a, Show b) {
        // Comparador responsavel por retornar o valor da comparação de date_added e title
        Date dataA = a.getData_added();
        Date dataB = b.getData_added();

        if (dataA == null && dataB == null)
            return a.getTitle().compareToIgnoreCase(b.getTitle());
        if (dataA == null)
            return 1;
        if (dataB == null)
            return -1;

        int compData = dataA.compareTo(dataB);
        if (compData != 0)
            return compData;

        return a.getTitle().compareToIgnoreCase(b.getTitle());
    }

    public static void quickSortParcial(Show[] shows, int esq, int dir, int k, int[] comp, int[] mov) {
        if (esq >= dir) return;

        int i = esq, j = dir;
        Show pivo = shows[(esq + dir) / 2];

        while (i <= j) {
            while (validacao(shows[i], pivo, comp) < 0) i++;
            while (validacao(shows[j], pivo, comp) > 0) j--;

            if (i <= j) {
                troca(shows, i, j, mov);
                i++;
                j--;
            }
        }

        if (esq < j && j >= k) {
            quickSortParcial(shows, esq, j, k, comp, mov);
        } else {
            quickSortParcial(shows, esq, j, k, comp, mov);
            quickSortParcial(shows, i, dir, k, comp, mov);
        }
    }

    private static void troca(Show[] arr, int i, int j, int[] mov) {
        Show temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        mov[0] += 3;
    }

    public static int validacao(Show a, Show b, int[] comp) {
        comp[0]++;
        Date da = a.getData_added();
        Date db = b.getData_added();

        if (da == null && db == null) return 0;
        if (da == null) return 1;
        if (db == null) return -1;

        int cmp = da.compareTo(db);
        if (cmp != 0) return cmp;

        comp[0]++;
        return a.getTitle().compareToIgnoreCase(b.getTitle());
    }

    public static void main(String[] args) {
        // Inicia o scanner com o UTF-8 para conseguir ler todos os caracteres
        Scanner ler = new Scanner(System.in, "UTF-8");
        // Registra o inicio do processamento do sistema
        long inicio = System.nanoTime();
        String entrada = ler.nextLine();
        Show[] shows = new Show[500];
        int tam = 0;
        while (!ehFim(entrada)) {
            shows[tam] = new Show(converteStr(entrada));
            tam++;
            entrada = ler.nextLine();
        }
        // Os valores de movimentações e comparações são armazenados em array para ser
        // passado por referencia
        int movi[] = { 0 };
        int comp[] = { 0 };
        // Valor de K para seleção parcial
        int k = 10;
        quickSortParcial(shows, 0, tam - 1, k, comp, movi);
        for (int i = 0; i < k; i++) {
            shows[i].imprimir();
        }
        ler.close();
        // calcula o tempo gasto, e inicia o processo de escrita no arquivo
        long fim = System.nanoTime();
        double tempoExec = (fim - inicio) / 1e6;
        try {
            FileWriter arq = new FileWriter("matricula_QuickSortParcial.txt");
            arq.write("869899" + '\t' + comp[0] + '\t' + movi[0] + '\t' + tempoExec);
            arq.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
