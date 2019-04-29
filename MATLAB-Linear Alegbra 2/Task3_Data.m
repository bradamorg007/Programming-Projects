
X = data;

%Plots all the dimensions overlayed

for i = 1:9 
   hold on 
    plot(X(:, i), X(:, i +1), '.')
    title ('Plot1 - 2D projection: data set graphed across 10-dimensions');
    xlabel ('Data Space')
    ylabel ('Data Space')
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

%Projecting Data onto sub-dimension(2D)

W_Reduce = W(:, 1:2);

sub_Dim = data * W_Reduce;


figure; plot(sub_Dim(:, 1), sub_Dim(:, 2), '.k');
title('2D Subspace: 10 - 2 dimensional redeuction ')
xlabel('Dimension 10 (PCA 1)')
ylabel('Dimension 9 (PCA 2)')







