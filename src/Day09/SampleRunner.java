package Day09;

public class SampleRunner {
    public static void main(String[] args) {

      /*  try(SampleResource sampleResource = new SampleResource()){
            sampleResource.hello();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        SampleRunner runner = new SampleRunner();
        runner.run();
        //System.gc();
    }

    public void run() {
        SampleResource sampleResource = new SampleResource();
        sampleResource.hello();
    }
}
