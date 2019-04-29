load('fake_fmri_data');

%Plotting the initial data of 1 voxel(x = 4, y = 1 time = 1-4) across the 
% the time stamps

subplot(4,1,1); plot( A(:,1));
subplot(4,1,2); plot( A(:,2));
subplot(4,1,3); plot( A(:,3));
subplot(4,1,4); plot( A(:,4));

%Store the solutions to the linear systems of each voxel for each of their 
% time stamps for each condition (1-4)

results = zeros(20, 20, 4);

for plane_x = 1:20
    for plane_y = 1:20
        
       results(plane_x, plane_y, :) = linsolve(A, squeeze(y(plane_x, plane_y, :)));
    end 
    
end 

%Add labels so the diagrams make sense
subplot(4,1,1); imagesc(results(:, :, 1))
h = colorbar;
ylabel(h, 'Activity Level')
title('Voxel activity - Condition 1')
xlabel('Voxel No (x20)')
ylabel('Voxel No (x20)')

subplot(4,1,2); imagesc(results(:, :, 2))
title('Voxel activity - Condition 2')
xlabel('Voxel No (x20)')
ylabel('Voxel No (x20)')
h = colorbar;
ylabel(h, 'Activity Level')

subplot(4,1,3); imagesc(results(:, :, 3))
title('Voxel activity - Condition 3')
xlabel('Voxel No (x20)')
ylabel('Voxel No (x20)')
h = colorbar;
ylabel(h, 'Activity Level')

subplot(4,1,4); imagesc(results(:, :, 4))
title('Voxel activity - Condition 4')
xlabel('Voxel No (x20)')
ylabel('Voxel  (x20)')
h = colorbar;
ylabel(h, 'Activity Level')



