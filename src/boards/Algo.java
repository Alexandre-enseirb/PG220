package boards;

import utils.IReader;

import java.util.ArrayList;

public class Algo {
    public static void main(String[] args) {
        String filename1= "src/fichier_XML/fournisseurs.xml";
        String filename2= "src/fichier_XML/clients.xml";
        ArrayList<Supplier> suppliers = transformSupplier(filename1);
        ArrayList<Client> clients = transformClient(filename2);
        System.out.println(suppliers.get(0).boards.get(1).date);

//        }
    }
    static ArrayList<Client> transformClient(String filename) {
        IReader reader =IReader.InstantiateXMLReader();
        IFactory cf = new ClientFactory();
        ArrayList<IGenerable> Users =  reader.read(cf,filename);
        System.out.println("Reading Clients : ");
        ArrayList<Client > client = new ArrayList<Client>();
        for(IGenerable c:Users){
            client.add((Client) c);
            System.out.println("Client" + ((Client) c).id);
        }

        for (Client cl : client){
            ArrayList<BoardData> lesplanche = new ArrayList<BoardData>();
            for(IGenerable p : cl.boards){

                lesplanche.add((ClientBoard) p);

            }
            cl.setBoards(lesplanche);
        }

        for(Client cl : client) {

            for (BoardData p : cl.boards) {
                Dimension dim = (Dimension) p.length;
                p.setLength(dim);
                dim = (Dimension) p.width;
                p.setWidth(dim);
            }
        }
        return client;
    }


    static ArrayList<Supplier> transformSupplier(String filename){
        IReader reader =IReader.InstantiateXMLReader();
        IFactory cf = new SupplierFactory();
        ArrayList<IGenerable> Users =  reader.read(cf,filename);
        System.out.println("Reading Suppliers");
        ArrayList<Supplier > suppliers = new ArrayList<Supplier>();
        for(IGenerable c:Users){
            suppliers.add((Supplier) c);
            System.out.println("Supplier" + ((Supplier) c).id);
        }

        for (Supplier cl : suppliers){
            ArrayList<BoardData> lesplanche = new ArrayList<BoardData>();
            for(IGenerable p : cl.boards){

                lesplanche.add((BoardData) p);

            }
            cl.setBoards(lesplanche);
        }

        for(Supplier cl : suppliers){

            for(BoardData p:cl.boards){
                Dimension dim = (Dimension) p.length;
                p.setLength(dim);
                dim = (Dimension) p.width;
                p.setWidth(dim);
            }

        }
        return suppliers;
    }
}

