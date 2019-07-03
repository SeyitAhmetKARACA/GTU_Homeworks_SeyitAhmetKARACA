package part1;

import part1.State.*;

public class Student {
    private State chronicIllness;
    private State fit;
    private State graduate;
    private State needingSleep;
    private State ready;
    private State unableToBecomeARodForAnAxe;

    private State state;

    public Student() {
        graduate = new Graduate(this);
        needingSleep = new NeedingSleep(this);
        fit = new Fit(this);
        chronicIllness = new ChronicIllness(this);
        ready = new Ready(this);
        unableToBecomeARodForAnAxe = new UnableToBecomeARodForAnAxe(this);

        state = ready;
    }

    public String getStateName() {
        return state.toString();
    }

    public void coffeeAndWork() {
        state.coffeeAndWork();
    }

    public void sleep() {
        state.sleep();
    }

    public void outTillLate() {
        state.outTillLate();
    }

    public void perseveranceAndHardWork() {
        state.perseveranceAndHardWork();
    }

    public void exercise() {
        state.exercise();
    }

    public void buyingGTX1080() {
        state.buyingGTX1080();
    }

    public void cheating() {
        state.cheating();
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getChronicIllnessState() {
        return chronicIllness;
    }

    public State getFitState() {
        return fit;
    }

    public State getGraduateState() {
        return graduate;
    }

    public State getNeedingSleepState() {
        return needingSleep;
    }

    public State getReadyState() {
        return ready;
    }

    public State getUnableToBecomeARodForAnAxeState() {
        return unableToBecomeARodForAnAxe;
    }
}