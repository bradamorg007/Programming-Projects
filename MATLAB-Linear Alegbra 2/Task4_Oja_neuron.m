
%Organising input vector 
x = ojax(:, 1);
y = ojax(:, 2);

input = [x' ; y'];

%Plot Current data as a scatter
figure(1)
hold on
plot(input(1, :), input(2, :),'.k' );%'.'=point values, 'K' black colour
title('Oja neuron - eigenvector tajectory')
xlabel(' X input stream')
ylabel('Y input stream')
axis equal %axis meauring line is equal
grid on 

%Initilisation of parameters 

learnrate = 0.5;
weights = [0.2 ; 0.6];
plot(0.2, 0.6, 'o'); %plots starting point of weights

for epoch = 1:6
    for i = 1:20000
       %neuron summation 
       sum = input(:, i)' * weights;
       %Equation to update weights
       weights = weights + learnrate*sum*(input(:, i) - sum * weights);
       plot(weights(1), weights(2), '.');  
    end 
end

plot(weights(1), weights(2), '+', 'markersize', 10);