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
        ArrayList<IGenerable> listClients = new ArrayList<>();
        int nbClients = Users.size();
        for(int i = 0;i<nbClients;i++){ // for each client in the arraylist
            int id = Integer.parseInt(String.valueOf(Users.get(i).get(0)));
            int nbElements = Users.get(i).size(); // each sublist is a board
            ArrayList<BoardData> boards = new ArrayList<>(); // we will store them in this arraylist


            int pid=-1;
            int nb=-1;
            String date="01.01.01";
            double price=-1;
            double len=-1;
            double wid=-1;
            double x=-1;
            double y=-1;
            boolean polygonal=false;
            ArrayList<Point> listP = null;
            for(int j = 1;j<nbElements;j++)
            {
                ArrayList<String> boardData = (ArrayList<String>) Users.get(i).get(j);
                try {
                    pid = Integer.parseInt(boardData.get(0));
                    nb = Integer.parseInt(boardData.get(1));
                    date = boardData.get(2);
                    price = Double.parseDouble(boardData.get(3));

                    switch(boardData.get(4)){
                        case "rect":
                            len = Double.parseDouble(boardData.get(5));
                            wid = Double.parseDouble(boardData.get(6));
                            break;
                        case "poly":
                            listP = new ArrayList<>();
                            polygonal=true;
                            x = Double.parseDouble(boardData.get(5));
                            y = Double.parseDouble(boardData.get(6));
                            listP.add(new Point(x,y));
                            break;
                    }


                } catch (NumberFormatException e) {
                    System.out.println("Invalid field. Client skipped.");
                    continue;
                }
                ClientBoard board = null;
                if (!polygonal){
                    board = new ClientBoard(id,pid,nb,date,price,len,wid);
                }else{
                    AnyPolygon poly = new AnyPolygon(listP);
                    Rectangle coveringRect = poly.getCoveringRectangle();
                    board = new ClientBoard(id, pid, nb, date, price, coveringRect.getLength(), coveringRect.getWidth());
                    board.setShape(poly);
                }

                if(board.allValid()&& board.length.getValue()>=board.width.getValue()){
                    System.out.println("true"+board.getLength().getValue()+" "+board.getWidth().getValue());
                    boards.add(board);
                }
                else{

                    System.out.println("false");
                    continue;
                }


            }
            Client c = new Client(id,boards);
            listClients.add(c);
        }
        return listClients;
    }

    /**
     * Converts the Igenerables to Clients
     * @param listClients list of IGenerables
     * @return list of Clients
     */
    static ArrayList<Client> IGenerable2Clients(ArrayList<IGenerable> listClients){
        ArrayList<Client > client = new ArrayList<Client>();
        for(IGenerable c:listClients){
            client.add((Client) c);
            System.out.println("Client" + ((Client) c).id);
        }
        return client;
    }
}
