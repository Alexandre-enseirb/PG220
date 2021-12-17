package boards;

import java.util.ArrayList;

/**
 * creates a Supplier from a list of values
 */
public class SupplierFactory implements IFactory{
    SupplierFactory(){

    }

    /**
     * Generates suppliers
     * @param Users all the variables needed to create our actors
     * @return a list of suppliers cast as IGenerables
     */
    @Override
    public ArrayList<IGenerable> generatePeople(ArrayList<ArrayList<Object>> Users) {
        ArrayList<IGenerable> suppliersList = new ArrayList<>();
        int nbSuppliers = Users.size();
        for(int i = 0;i<nbSuppliers;i++){
            int id = Integer.parseInt(String.valueOf(Users.get(i).get(0)));
            int nbElem = Users.get(i).size();
            ArrayList<BoardData> boards = new ArrayList<>();
            int pid=-1;
            int nbr=-1;
            String date="01.01.01";
            double price=-1;
            double len=-1;
            double wid=-1;
            for(int j = 1;j<nbElem;j++)
            {
                ArrayList<String> boardData = (ArrayList<String>) Users.get(i).get(j);
                try {

                    pid = Integer.parseInt(boardData.get(0));
                    nbr = Integer.parseInt(boardData.get(1));
                    date = boardData.get(2);
                    price = Double.parseDouble(boardData.get(3));
                    len = Double.parseDouble(boardData.get(4));
                    wid = Double.parseDouble(boardData.get(5));

                } catch(NumberFormatException e) {
                    System.out.println("Invalid field. Supplier skipped.");
                }
                BoardData board = new SupplierBoard(id, pid,nbr,date,price,len,wid);
                if(board.allValid()&& board.length.getValue()>=board.width.getValue()){

                    boards.add(board);
                }
                else{

                    System.out.println("Invalid supplier. Skipped.");
                    continue;
                }

            }
            Supplier c = new Supplier(id,boards);
            suppliersList.add(c);
        }
        return suppliersList;
    }

    /**
     * Converts all the suppliers to Supplier
     * @param suppliersList list of Suppliers cast as IGenerables
     * @return a list of Suppliers
     */
    static ArrayList<Supplier> IGenerable2Suppliers(ArrayList<IGenerable> suppliersList){
        ArrayList<Supplier > suppliers = new ArrayList<Supplier>();
        for(IGenerable c:suppliersList){
            suppliers.add((Supplier) c);
        }
        return suppliers;
    }

}
