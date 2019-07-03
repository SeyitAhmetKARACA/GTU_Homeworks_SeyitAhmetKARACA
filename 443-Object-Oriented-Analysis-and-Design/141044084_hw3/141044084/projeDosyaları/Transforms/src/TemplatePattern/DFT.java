package TemplatePattern;

public class DFT extends Transform {
    long one;

    /**
     * Template method
     * DFT hesaplama
     */
    @Override
    public void exec() {
        one = System.currentTimeMillis();
        int N = items.size();
        double T = 2.0;
        double data[] = new double[2*N];
        for(int i=0; i<N; ++i) {
            data[2*i] = items.get(i)*Math.cos(4.0*Math.PI*i*T/N);
            data[2*i+1] = 0.0;
        }
        double X[] = new double[2*N];
        double omega;
        int k, ki, kr, n;
        omega = 2.0*Math.PI/N;

        for(k=0; k<N; k++) {
            kr = 2*k;
            ki = 2*k + 1;
            X[kr] = 0.0;
            X[ki] = 0.0;
            for(n=0; n<N; ++n) {
                X[kr] += items.get(n)*Math.cos(omega*n*k) + data[2*n+1]*Math.sin(omega*n*k);
                X[ki] += -items.get(n)*Math.sin(omega*n*k) + data[2*n+1]*Math.cos(omega*n*k);
            }
            first.add(X[kr]);
            second.add(X[ki]);
        }
    }

    /**
     * template method design pattern'de
     * ek ozellik
     */
    @Override
    public void hook() {
        System.out.println("Would you like to see time of execution of Discrete Cosine Transform? [y/n]");
        if(getOption())
            System.out.println(System.currentTimeMillis()-one);

    }

}
