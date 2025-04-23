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
        if (linha.charAt(pos) == '"') {
            pos++;
            while (pos < linha.length() && linha.charAt(pos) != '"') {
                titulo += linha.charAt(pos);
                pos++;
            }
            pos+= 2;
        } else {
            while (pos < linha.length() && linha.charAt(pos) != ',') {
                titulo += linha.charAt(pos);
                pos++;
            }
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
            diretor = "NaN";
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
                        pos += 2;
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
            elenco = new String[1];
            elenco[0] = "NaN";
            pos++;
        }
        setCast(elenco);
        // País
        String pais = "";
        if (pos < linha.length()) {
            if (linha.charAt(pos) == '"') {
                pos++;
                while (linha.charAt(pos) != '"' && linha.length() > pos) {
                    pais += linha.charAt(pos);
                    pos++;
                }
                if (linha.charAt(pos) == '"') {
                    pos += 2;
                }
            } else if (linha.charAt(pos) != ',') {
                while (linha.charAt(pos) != ',' && linha.length() > pos) {
                    pais += linha.charAt(pos);
                    pos++;
                }
                pos++;
            }
            else{
                pais = "NaN";
                pos++;
            }
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
                while (pos < linha.length() && linha.charAt(pos) != ',' && linha.charAt(pos) != '"') {
                    categorias[categoria] += linha.charAt(pos);
                    pos++;
                }
                if (pos < linha.length() && linha.charAt(pos) == ',') {
                    pos += 2;
                    categoria++;
                }
            }
        }
        setListed(categorias);
    }

    public Show(String entrada) {
        ler(entrada);
    }

    public Show clone() {
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

    public void OrdenaAlfa(String[] vetor) {
        int n = vetor.length;
        boolean trocou;
        do {
            trocou = false;
            for (int i = 0; i < n - 1; i++) {
                if (vetor[i].compareTo(vetor[i + 1]) > 0) {
                    String temp = vetor[i];
                    vetor[i] = vetor[i + 1];
                    vetor[i + 1] = temp;
                    trocou = true;
                }
            }
            n--;
        } while (trocou);
    }

    public void imprimir() {
        // antes de começar a impressão, ordena os vetores que precisam ser ordenados
        OrdenaAlfa(cast);
        OrdenaAlfa(listed_in);
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

public class Psequencial{
    public static boolean ehFim(String entrada){
        // Função responsavel por verificar se a entrada recebida é FIM
        Boolean fim = false;
        if(entrada.length() == 3){
            if(entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M'){
                fim = true;
            }
        }
        return fim;
    }

    public static int converteStr(String entrada){
        // COnverte a entrada que esta em formato de string, para um valor int que será usado como chave para ler o csv
        int valor = 0;
        int multiplicador = 1;
        for(int i = entrada.length() - 1; i > 0; i--){
            int numero = entrada.charAt(i) - '0';
            valor += numero * multiplicador;
            multiplicador *= 10;
        }
        return valor;
    }

    public static void main(String[] args){
        // Inicia o scanner com o UTF-8 para conseguir ler todos os caracteres
        Scanner ler = new Scanner(System.in, "UTF-8");
        // Registra o inicio do processamento do sistema
        long inicio = System.nanoTime();
        String entrada = ler.nextLine();
        Show[] shows = new Show[500];
        int tam = 0;
        while(!ehFim(entrada)){
            shows[tam] = new Show(converteStr(entrada));
            tam++;
            entrada = ler.nextLine();
        }
        entrada = ler.nextLine();
        int comparacoes = 0;
        while(!ehFim(entrada)){
            boolean encontrado = false;
            int i = 0;
            while(i < tam && !encontrado){
                // Aumenta o numero de comparações a cada vez que o loop reinciar, para contar todas as comparações
                comparacoes++;
                if(shows[i].getTitle().equals(entrada)){
                    encontrado = true;
                }
                i++;
            }
            if(encontrado){
                System.out.println("SIM");
            }
            else{
                System.out.println("NAO");
            }
            entrada = ler.nextLine();
        }
        ler.close();
        // calcula o tempo gasto, e inicia o processo de escrita no arquivo
        long fim = System.nanoTime();
        double tempoExec = (fim - inicio) / 1e6;
        try{
            FileWriter arq = new FileWriter("matrícula_sequencial.txt");
            arq.write("869899" + '\t' + tempoExec + '\t' + comparacoes);
            arq.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
