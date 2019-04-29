function [gridworld, relocation_list, mean_segregation,percent_satisfied ] = satisfaction(gridworld, tolerance)

[rows, cols] = size(gridworld);
a = 0;
for r = 2:rows-1
    for c = 2:cols-1
        row_pos = r;
        col_pos = c;
        scope = gridworld(row_pos, col_pos);
        if isequal(scope,1) || isequal(scope, -1)
            a = a+1;
            mask = gridworld(row_pos-1:(row_pos-1)+2, col_pos-1:(col_pos-1)+2);
            mask(2, 2) = NaN;
            mask = reshape(mask, [1, 9]);
            mask(isnan(mask)) = [];
            mask( :, ~any(mask,1) ) = [];
            num_neighbours = length(mask);
            same = 0;
            for i = 1:num_neighbours
                if isequal(mask(i), scope)
                    same = same + 1;
                end 
            end 
            
            if isequal(num_neighbours, 0)
                percent_same = tolerance;
            elseif num_neighbours > 0
                percent_same = same/num_neighbours;
            end 
            
            relocation_list(a,1) = percent_same;
            
            if percent_same >= tolerance
                relocation_list(a,2) = 100;
                relocation_list(a,3:4) = [row_pos, col_pos];  
            elseif percent_same < tolerance
                relocation_list(a,2) = -100;
                relocation_list(a,3:4) = [row_pos, col_pos];   
            end 
        end 
    end 
end 

mean_segregation = (mean(relocation_list(:, 1)))*100;
[ro1, co1] = find(relocation_list==100);
percent_satisfied = (length(ro1)/length(relocation_list(:, 2)))*100;
end 