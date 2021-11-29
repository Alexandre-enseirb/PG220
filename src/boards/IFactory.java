package boards;

import java.util.ArrayList;

public interface IFactory {
    IGenerable generatePeople(int id, ArrayList<IGenerable> lesplaches);
    IGenerable generatePlanche(int id,int nombre,String date,Double prix,IGenerable len,IGenerable wid);
    IGenerable generateDim(Double len);


    //Igenerate readPeople();
    static IFactory InstantiateClient(){
        return new ClientFactory();
    }
}
