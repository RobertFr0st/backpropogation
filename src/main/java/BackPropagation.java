/**
 * Created by nick on 4/3/17.
 */
public class BackPropagation {
  public static void main(String [] args)
  {
    Integer hidden_layer_count = Integer.parseInt(args[0]);
    Integer learning_rate = Integer.parseInt(args[1]);
    String training_filename = args[2];
    String validation_filename = args[3];
    String testing_filename = args[4];
    Integer epoch_count = Integer.parseInt(args[5]);
    Integer input_count = Integer.parseInt(args[6]);
    Integer assignment_part_number = Integer.parseInt(args[7]);

    Integer[] hidden_layer_neuron_counts = new Integer[args.length - 8];
    for (int i = 8; i < args.length; i++) {
      hidden_layer_neuron_counts[i - 8] = Integer.parseInt(args[i]);
    }

    Neral_Network neral_network = new
      Neral_Network(hidden_layer_count, hidden_layer_neuron_counts, learning_rate, input_count);
    //1, 2, or 3
    neral_network.setOperator(assignment_part_number);
    for(int i = 0; i < epoch_count; i++){
      neral_network.trainNet(training_filename, input_count);
      neral_network.validateNet(validation_filename, input_count);
    }
    neral_network.testNet(testing_filename, input_count);
  }
}
