package boards;

import java.util.ArrayList;

public class ClientFactory implements IFactory{
    ClientFactory(){

    }



    @Override
    public ArrayList<IGenerable> generatePeople(ArrayList<ArrayList<Object>> Users) {
        ArrayList<IGenerable> lesclients = new ArrayList<>();
        int nbr_Clients = Users.size();
        for(int i = 0;i<nbr_Clients;i++){
            int id = Integer.parseInt(String.valueOf(Users.get(i).get(0)));
            int nbr_element = Users.get(i).size();
            ArrayList<BoardData> boards = new ArrayList<>();
            for(int j = 1;j<nbr_element;j++)
            {
                ArrayList<String> info_Planche = (ArrayList<String>) Users.get(i).get(j);
                int pid = Integer.parseInt(info_Planche.get(0));
                int nbr = Integer.parseInt(info_Planche.get(1));
                String date = info_Planche.get(2);
                double prix = Double.parseDouble(info_Planche.get(3));
                double len = Double.parseDouble(info_Planche.get(4));
                double wid = Double.parseDouble(info_Planche.get(5));

                BoardData board = new ClientBoard(id, pid,nbr,date,prix,len,wid);
                boards.add(board);
            }
            Client c = new Client(id,boards);
            lesclients.add(c);
        }
        return lesclients;
    }

    static ArrayList<Client> IGenerable2Clients(ArrayList<IGenerable> lesclients){
        ArrayList<Client > client = new ArrayList<Client>();
        for(IGenerable c:lesclients){
            client.add((Client) c);
            System.out.println("Client" + ((Client) c).id);
        }
        return client;
    }
}
