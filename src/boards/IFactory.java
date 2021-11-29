package boards;

import java.util.ArrayList;

public interface IFactory {
    ArrayList<IGenerable> generatePeople(ArrayList<ArrayList<Object>> Users);



    //Igenerate readPeople();
    static IFactory InstantiateClient(){
        return new ClientFactory();
    }
}
