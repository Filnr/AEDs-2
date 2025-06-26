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
    private Date date_added;
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

    public void setDate(Date date_added) {
        this.date_added = date_added;
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

    public Date getDate_added() {
        return this.date_added;
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
        date_added = null;
        release_year = 0;
        rating = "NaN";
        duration = "NaN";
        listed_in = null;
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
        String caminho = "/tmp/disneyplus.csv";
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
            this.date_added = campos[6].equals("") ? null : dateFormat.parse(campos[6]);
        } catch (Exception e) {
            this.date_added = null;
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
        clonado.date_added = this.date_added;
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
        SimpleDateFormat date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        String dateFormatada;
        if (getDate_added() != null) {
            dateFormatada = date.format(getDate_added());
        } else {
            dateFormatada = "NaN";
        }
        System.out.printf("=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##\n",
                show_ID, title, type, director, Arrays.toString(cast), country, dateFormatada, release_year,
                rating, duration, Arrays.toString(listed_in));
    }
}

class Tabela {
    private Show tabela[];
    // Variaveis que expressão cada porção e tamanho da tabela
    private final int tamTab = 21;
    private final int tamRes = 9;
    private final int totalTam = tamRes + tamTab;
    private int comps;

    public Tabela() {
        // Construtor padrão
        tabela = new Show[totalTam];
        for (int i = 0; i < totalTam; i++) {
            tabela[i] = null;
        }
        this.comps = 0;
    }

    public int hash(String title) {
        // Função que calcula a hash de um title
        int soma = 0;
        for (int i = 0; i < title.length(); i++) {
            soma += (int) title.charAt(i);
        }
        return soma % tamTab;
    }

    public void inserir(Show show) {
        // utiliza do title para buscar na hash a posição do show
        int hash = hash(show.getTitle());
        if (tabela[hash] == null) {
            tabela[hash] = show;
        } else if (tabela[hash] == show) {
            return;
        } else {
            // Se o hash ja estiver ocupado, insere na primeira posição vazia que encontrar na reserva
            int j = tamTab;
            while (j < totalTam) {
                if (tabela[j] == null) {
                    tabela[j] = show;
                    j = totalTam;
                }
                j++;
            }
        }
    }

    public boolean pesquisar(String title) {
        // utiliza do title para buscar na hash a posição do show
        int hash = hash(title);
        boolean encontrado = false;
        comps++;
        System.out.printf(" (Posicao: %d)", hash);
        // Verica se a posição na tabela é null, para economizar comparações
        // desnecessarias
        if (tabela[hash] == null) {
            encontrado = false;
        } else if (tabela[hash].getTitle().equals(title)) {
            encontrado = true;
        } else {
            int i = tamTab;
            // Se o elemento não foi encontrado na hash, busca na reserva até o final
            while (i < totalTam && !encontrado) {
                comps++;
                if (tabela[i].getTitle().equals(title)) {
                    encontrado = true;
                }
                i++;
            }
        }
        return encontrado;
    }

    public int getComps() {
        return comps;
    }
}

public class Principal {
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

    public static void main(String[] args) {
        // Inicia o scanner com o UTF-8 para conseguir ler todos os caracteres
        Scanner ler = new Scanner(System.in, "UTF-8");
        // Registra o inicio do processamento do sistema
        long inicio = System.nanoTime();
        String entrada = ler.nextLine();
        Tabela tabelaHash = new Tabela();
        while (!ehFim(entrada)) {
            Show show = new Show(entrada);
            tabelaHash.inserir(show);
            entrada = ler.nextLine();
        }
        entrada = ler.nextLine();
        while (!ehFim(entrada)) {
            if (tabelaHash.pesquisar(entrada)) {
                System.out.println(" SIM");
            } else {
                System.out.println(" NAO");
            }
            entrada = ler.nextLine();
        }
        ler.close();
        // calcula o tempo gasto, e inicia o processo de escrita no arquivo
        long fim = System.nanoTime();
        double tempoExec = (fim - inicio) / 1e6;
        try {
            FileWriter arq = new FileWriter("869899_hashReserva.txt");
            arq.write("869899" + '\t' + tempoExec + '\t' + tabelaHash.getComps());
            arq.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
