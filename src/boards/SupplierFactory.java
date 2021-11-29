package boards;

import java.util.ArrayList;

public class SupplierFactory implements IFactory{
    SupplierFactory(){

    }

    @Override
    public IGenerable generatePeople(int id, ArrayList<IGenerable> lesplaches) {
        ArrayList<BoardData> lesp = new ArrayList<>();
        for(IGenerable p:lesplaches){
            lesp.add((BoardData) p);
        }
        return new Supplier(id,lesp);
    }

    @Override
    public IGenerable generatePlanche(int id, int nombre, String date, Double prix, IGenerable len, IGenerable wid)  {
        return (IGenerable) new ClientBoard(id,nombre,date,prix,len,wid);
    }

    @Override
    public IGenerable generateDim(Double len) {
        return (IGenerable) new Dimension(len);
    }
}
