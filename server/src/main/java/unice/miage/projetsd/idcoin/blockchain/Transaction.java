package unice.miage.projetsd.idcoin.blockchain;

class Transaction {

    private int id;
    private long hash;
    private String type;
    private Input[] inputs;
    private Output[] outputs;

    Transaction(int id, long hash, String type){
        this.id = id;
        this.hash = hash;
        this.type = type;
        this.inputs = new Input[8]; // Demystify (8 is arbitrary)
        this.outputs = new Output[8];
    }

    private Transaction(int id, long hash, String type, Input[] inputs, Output[] outputs){
        this.id = id;
        this.hash = hash;
        this.type = type;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public void toHash(){
        //return CryptoUtil.hash(this.id + this.type + JSON.stringify(this.data));
    }

    public boolean check(){
        return false;
    }

    public static Transaction fromJSON(String json){
        return new Transaction(0, 0, "Troll", new Input[2], new Output[2]);
    }
}