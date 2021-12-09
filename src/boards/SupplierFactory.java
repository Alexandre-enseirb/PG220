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
            for(int j = 1;j<nbr_element;j++)
            {
                ArrayList<String> info_Planche = (ArrayList<String>) Users.get(i).get(j);
                int pid = Integer.parseInt(info_Planche.get(0));
                int nbr = Integer.parseInt(info_Planche.get(1));
                String date = info_Planche.get(2);
                double prix = Double.parseDouble(info_Planche.get(3));
                double len = Double.parseDouble(info_Planche.get(4));
                double wid = Double.parseDouble(info_Planche.get(5));

                BoardData board = new SupplierBoard(id, pid,nbr,date,prix,len,wid);
                boards.add(board);
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
