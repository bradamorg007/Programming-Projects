X = data2;
%figure; hist(X(:, 1))

%Plots all the dimensions overlayed
for i = 1:9 
   hold on 
    plot(X(:, i), X(:, i +1), '.')
    
end 

hold off


Cov = cov(X);

%Calculate Eigenvalues of C
[W, Lamda] = eig(Cov);
 u = diag(Lamda)/trace(Lamda) * 100;
 figure; bar(u)
xlabel('Dimensions')
ylabel('Percentage of Explained Variance')

%Reordering W and Lamda 

W = W(:, end: -1:1);
Lamda = Lamda(:, end: -1: 1);

%Add a percentage graph of variance on youtube vid

%Projecting Data onto sub-dimension(2D)

W_Reduce = W(:, 1:2);

sub_Dim = data2 * W_Reduce;

%Ploting the reduced data 


figure; plot(sub_Dim(:, 2), sub_Dim(:, 1), '.k');
title('2D Subspace: 10 - 2 dimensional redeuction ')
xlabel('Dimension 9 (PCA 2)')
ylabel('Dimension 10 (PCA 1)')
