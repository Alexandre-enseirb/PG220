package boards;

import java.util.ArrayList;

public class SupplierFactory implements IFactory{
    SupplierFactory(){

    }


    @Override
    public ArrayList<IGenerable> generatePeople(ArrayList<ArrayList<Object>> Users) {
        ArrayList<IGenerable> lesSuppliers = new ArrayList<>();
        int nbr_Suppliers = Users.size();
        for(int i = 0;i<nbr_Suppliers;i++){
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

                } catch(NumberFormatException e) {
                    System.out.println("Invalid field. Supplier skipped.");
                }
                BoardData board = new SupplierBoard(id, pid,nbr,date,prix,len,wid);
                if(board.allValid()&& board.length.getValue()>=board.width.getValue()){
                    System.out.println("true"+board.getLength().getValue()+" "+board.getWidth().getValue());
                    boards.add(board);
                }
                else{

                    System.out.println("false");
                    continue;
                }
//                finally {
//                    BoardData board = new SupplierBoard(id, pid,nbr,date,prix,len,wid);
//                    if (board.allValid()) {
//                        boards.add(board);
//                    }else{
//                        System.out.println("Invalid value, skipped.");
//                    }
//                }


            }
            Supplier c = new Supplier(id,boards);
            lesSuppliers.add(c);
        }
        return lesSuppliers;
    }

    static ArrayList<Supplier> IGenerable2Suppliers(ArrayList<IGenerable> lesSuppliers){
        ArrayList<Supplier > suppliers = new ArrayList<Supplier>();
        for(IGenerable c:lesSuppliers){
            suppliers.add((Supplier) c);
            System.out.println("Supplier" + ((Supplier) c).id);
        }
        return suppliers;
    }

}
