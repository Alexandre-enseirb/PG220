package boards;

import utils.IReader;

import java.util.ArrayList;

public class Algo {
    public static void main(String[] args) {
        String filename= "src/fichier_XML/clients.xml";
        IReader reader =IReader.InstantiateXMLReader();
        //IFactory cf = IFactory.InstantiateClient();
        IFactory cf = new ClientFactory();
        ArrayList<IGenerable> cc =  reader.read(cf,filename);
        //ArrayList<Client> c = (Client) f;
        ArrayList<Client > client = new ArrayList<Client>();
        for(IGenerable c:cc){
            client.add((Client) c);
            System.out.println(((Client) c).id);
        }
        //System.out.println(client.size());
        for (Client cl : client){
            ArrayList<BoardData> lesplanche = new ArrayList<BoardData>();
            for(IGenerable p : cl.boards){

                lesplanche.add((ClientBoard) p);

            }
            cl.setBoards(lesplanche);
        }

        for(Client cl : client){
            // System.out.println(cl.listP.get(0).date);
            for(BoardData p:cl.boards){
                Dimension dim = (Dimension) p.length;
                p.setLength(dim);
                dim = (Dimension) p.width;
                p.setWidth(dim);
            }
            System.out.println(cl.boards.get(0).length.getValue());

        }
    }
}

