package TemplatePattern;

public class DCT extends Transform {

    /**
     * Template method
     * DCT hesaplama
     */
    @Override
    public void exec() {

        double temp = 0 ;
        double pdn = Math.PI/items.size();
        for(int k=0; k < items.size(); k++){
            temp = 0;
            for(int n = 0 ; n < items.size(); n++) {
                temp =+ items.get(n)*Math.cos(pdn*k*(n+(1/2)));
            }
            first.add((double)((int)temp));
            second.add(temp-((int)temp));
        }
    }

    /**
     * DCT'de kullanici nin sure gormesi engelleniyor
     * @return
     */
    @Override
    public boolean getOption(){
        return false;
    }

    /**
     * template method design pattern'de
     * ek ozellik
     */
    @Override
    public void hook() {
        return;
    }
}
