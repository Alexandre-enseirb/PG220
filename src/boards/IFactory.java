package boards;

import java.util.ArrayList;

/**
 * Interface to generate IGenerables
 */
public interface IFactory {

    /**
     * Transforms a list of lists of objects into a list of IGenerables
     * @param Users all the variables needed to create our actors
     * @return a list of IGenerables
     */
    ArrayList<IGenerable> generatePeople(ArrayList<ArrayList<Object>> Users);



    //Igenerate readPeople();

    /**
     * instantiates a ClientFactory
     * @return a ClientFactory
     */
    static IFactory InstantiateClient(){
        return new ClientFactory();
    }
}
