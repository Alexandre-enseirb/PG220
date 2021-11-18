package boards;

class Price implements Validable {

    private int value;

    Price(){
        this.value = 0;
    }

    Price(int val){
        this.value = val;
    }

    public boolean isValid(){
        if (this.value <= 0){
            return false;
        }
        return true;
    }
}
