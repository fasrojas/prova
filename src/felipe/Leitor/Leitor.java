package felipe.Leitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Leitor {

    public ArrayList read(String caminho) throws IOException, ParseException, URISyntaxException {
        ArrayList list = new ArrayList();
        File file = new File(getClass().getResource(caminho).toURI());
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        //equanto houver mais linhas
        while (br.ready()) {
            //l� a proxima linha
            String linha = br.readLine();

            String[] dados = linha.split(" ");
            int i = 0;

            Game game = new Game();

            while (i < dados.length) {

                if (i == 0) {
                    SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
                    try {
                        java.sql.Date data = new java.sql.Date(df.parse(dados[i]).getTime());
                        game.setDia(data);
                    } catch (Exception e) {
                    }
                } else if (i == 1) {
                    try {
                        game.setHora(dados[2]);
                    } catch (Exception e) {
                    }
                } else if (i == 2) {
                    try {
                        game.setInf1(dados[3]);
                    } catch (Exception e) {
                        game.setInf1("");
                    }
                } else if (i == 3) {
                    try {
                        game.setInf2(dados[4]);
                    } catch (Exception e) {
                        game.setInf2("");
                    }
                } else if (i == 4) {
                    try {
                        game.setInf3(dados[5]);
                    } catch (Exception e) {
                        game.setInf3("");
                    }
                } else if (i == 5) {
                    try {
                        game.setInf4(dados[6]);
                    } catch (Exception e) {
                        game.setInf4("");
                    }
                } else if (i == 6) {
                    try {
                        game.setInf5(dados[7]);
                    } catch (Exception e) {
                        game.setInf5("");
                    }
                } else if (i == 7) {
                    try {
                        game.setInf6(dados[8]);
                    } catch (Exception e) {
                        game.setInf6("");
                    }
                }



                i++;

            }
            list.add(game);
            //faz algo com a linha
        }

        br.close();
        fr.close();
        return list;

    }

    public void contaPontuacao(ArrayList lista) {


        int i = 0;
        StringBuffer bf = new StringBuffer();

        bf.append("_______________RANKING_________________\n");
        bf.append("\n");
        bf.append("\n");
        int qtd_de_mortos = 0;
        HashMap matadores = new HashMap();
        HashMap morredores = new HashMap();
        while (i < lista.size()) {

            Game game = (Game) lista.get(i);
// Montar o ranking de cada partida, com a quantidade assassinatos e a quantidade de mortes de cada jogador;
            if (game.getInf1().contains("New") && game.getInf2().contains("match")) {
                bf.append("_____________Inicio___________________\n");
                bf.append("Partida Numero " + game.getInf3() + "\n");
                qtd_de_mortos = 0;


            }

            if (!game.getInf1().contains("<WORLD>") && game.getInf2().contains("killed")) {
                qtd_de_mortos++;
            }

            if (!game.getInf1().contains("<WORLD>") && !game.getInf1().contains("New")
                    && !game.getInf1().contains("Match")) {
                String assassino = game.getInf1();
                if (matadores.containsKey(assassino) == false) {
                    matadores.put(assassino, 1);
                } else {
                    int contador = (Integer) matadores.get(assassino);
                    matadores.put(assassino, contador);
                }
                String morto = game.getInf3();
                if (morredores.containsKey(morto) == false) {
                    morredores.put(morto, 1);
                } else {
                    int contador = (Integer) morredores.get(morto);
                    morredores.put(morto, contador);
                }

            }

            if (game.getInf1().contains("Match") && game.getInf4().contains("ended")) {
                
                for (Iterator it = matadores.keySet().iterator(); it.hasNext();) {
                    Object key = it.next();
                    Object item = matadores.get(key);
                    bf.append(key.toString()+" matou "+Integer.valueOf(String.valueOf(item))+" pessoas \n");
                   
                    
                }
                
                
                  for (Iterator it = morredores.keySet().iterator(); it.hasNext();) {
                    Object key = it.next();
                    Object item = morredores.get(key);
                    String awa = "";
                    int awards = Integer.valueOf(String.valueOf(item));
                   
                    bf.append(key.toString()+" morreu "+Integer.valueOf(String.valueOf(item))+" vezes \n");
                     if( awards == 0){
                        awa = key.toString()+" Ganhou um awards por não ter morrido";
                    }
                    
                }
                  
                  morredores = new HashMap();
                  matadores = new HashMap();
               
                bf.append("Quantidade de Mortos na partida = " + qtd_de_mortos + "\n");
                bf.append("");

                bf.append("             fim                      \n");


            }

            i++;
        }

        System.out.println(bf.toString());
    }

    public static void main(String args[]) throws IOException, ParseException, URISyntaxException {

        Leitor leitor = new Leitor();
        ArrayList lista = new ArrayList();
        ArrayList read = leitor.read("/log/log.log");
        leitor.contaPontuacao(read);


    }
}
