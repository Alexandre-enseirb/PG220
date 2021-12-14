package boards;

import java.util.ArrayList;

/**
 * creates a Client via the list given by the reader
 */
public class ClientFactory implements IFactory{
    /**
     * Constructor
     */
    ClientFactory(){

    }


    /**
     * generates a list of clients
     * @param Users list of list of variables
     * @return list of IGenerables
     */
    @Override
    public ArrayList<IGenerable> generatePeople(ArrayList<ArrayList<Object>> Users) {
        ArrayList<IGenerable> lesclients = new ArrayList<>();
        int nbr_Clients = Users.size();
        for(int i = 0;i<nbr_Clients;i++){
            int id = Integer.parseInt(String.valueOf(Users.get(i).get(0)));
            int nbr_element = Users.get(i).size();
            ArrayList<BoardData> boards = new ArrayList<>();
            int pid=-1;
            int nbr=-1;
            String date="01.01.01";
            double prix=-1;
            double len=-1;
            double wid=-1;
            for(int j = 1;j<nbr_element;j++)
            {
                ArrayList<String> info_Planche = (ArrayList<String>) Users.get(i).get(j);
                try {
                    pid = Integer.parseInt(info_Planche.get(0));
                    nbr = Integer.parseInt(info_Planche.get(1));
                    date = info_Planche.get(2);
                    prix = Double.parseDouble(info_Planche.get(3));
                    len = Double.parseDouble(info_Planche.get(4));
                    wid = Double.parseDouble(info_Planche.get(5));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid field. Client skipped.");
                } finally {
                    BoardData board = new ClientBoard(id, pid,nbr,date,prix,len,wid);
                    if (board.allValid()) {
                        boards.add(board);
                    }else{
                        System.out.println("Invalid board. Skipped.");
                    }
                }



            }
            Client c = new Client(id,boards);
            lesclients.add(c);
        }
        return lesclients;
    }

    /**
     * Converts the Igenerables to Clients
     * @param lesclients list of IGenerables
     * @return list of Clients
     */
    static ArrayList<Client> IGenerable2Clients(ArrayList<IGenerable> lesclients){
        ArrayList<Client > client = new ArrayList<Client>();
        for(IGenerable c:lesclients){
            client.add((Client) c);
            System.out.println("Client" + ((Client) c).id);
        }
        return client;
    }
}
