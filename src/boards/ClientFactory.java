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

                    //<<<<<<< Updated upstream

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

//=======
//                   len = Double.parseDouble(info_Planche.get(4));
//                    wid = Double.parseDouble(info_Planche.get(5));
//               }catch (NumberFormatException n){
//                   System.out.println("invalid, skip");
//                   continue;
//               }
//
//                   BoardData board = new ClientBoard(pid,nbr,date,prix,len,wid);
//                   if(board.allValid()|| board.length.getValue()<board.width.getValue()){
//                       System.out.println("true");
//                       boards.add(board);
//                   }
//                    else{
//
//                       System.out.println("false");
//                        continue;
//                   }
////                boards.add(board);
//>>>>>>> Stashed changes


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
