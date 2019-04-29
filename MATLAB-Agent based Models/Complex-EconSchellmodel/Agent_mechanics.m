function [AGENT_DYNAMICS, mean_segregation, percent_satisfied] = Agent_mechanics(gridworld, resourceworld, Id_grid,...
                                                                                 AGENT_DYNAMICS, relocation_cost,...
                                                                                 NobodyAround_Rule, Avoid_total_emptyspace)
% For Notes on Variables such a s Mask please see the 2nd page of the
% report
[arows, acols] = size(AGENT_DYNAMICS);

[rows, cols] = size(gridworld);
a = 0;
for r = 2:rows-1
    for c = 2:cols-1
        row_pos = r;
        col_pos = c;
        scope = gridworld(row_pos, col_pos);
        
        if isequal(scope,1) || isequal(scope, -1)
            a = a+1;
            Agent_id = Id_grid(r, c);
            resource_pos = resourceworld(r, c);
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
                   
             [m, d] = find(AGENT_DYNAMICS(1, :) == Agent_id);
             agent_data = AGENT_DYNAMICS(:, d);
                if isempty(d)
                    break
                end 
            
            tolerance = agent_data(3);
            survival_needs = agent_data(5);
            available_resources = agent_data(6);
            work_status = agent_data(7);
            resource_threshold = agent_data(8);
            
            reset_tol = tolerance;
            
            % How to Dela with Empty Space Calculations and percentage
            % satisfaction and segregation calculation
            if isequal(num_neighbours, 0)
                percent_same = NobodyAround_Rule;
                if ~strcmpi(Avoid_total_emptyspace, 'yes')
                tolerance = 0;
                end 
               
            elseif num_neighbours > 0
                percent_same = same/num_neighbours;
            end 
            
            agent_data(4) = percent_same;
            
            % How agents should choose an appropriate action based upon
            % their survival, resource pool, minority avoidance and
            % finacial sensibility preferences
            
            % 100 Means agent will remain and not move
            % -100 means agent wants to relocate
            if available_resources <= resource_threshold && percent_same >= tolerance
                    % neighboured okay but resources low, make a recourse
                    % choice
                    if resource_pos > 2 %if their are resouces then work
                        agent_data(7) = 1; 
                        agent_data(9) = 100;
                        agent_data(10) = row_pos;
                        agent_data(11) = col_pos;
                        agent_data(15) = agent_data(15) + 1; % remains due resource poverty ;
                        
                    else
                        % choice to move to new location
                        agent_data(9) = -100;
                        agent_data(10) = row_pos;
                        agent_data(11) = col_pos;
                        agent_data(13) = agent_data(13) + 1; % relocations due resource poverty ;
                    end 
            elseif available_resources > resource_threshold && percent_same >= tolerance
                %agent has enough resources and is satisfied with neighbours
                    agent_data(9) = 100;
                     if resource_pos > 2 
                        agent_data(7) = 1; %if their are resouces then work
                         agent_data(10) = row_pos;
                         agent_data(11) = col_pos;
                         agent_data(16) = agent_data(16) + 1; % Remains Due resouce wealth
                     end 
            elseif available_resources > resource_threshold && percent_same < tolerance
                       % Resources are okay but agent not
                       % happy with neighbours, then relocate if one can
                       % afford it
                       
                       okay_to_move = available_resources - relocation_cost;
                       if okay_to_move > resource_threshold
                           if resource_pos > 2*available_resources
                               agent_data(7) = 1; %if their are profits to be gained then work
                               agent_data(9) = 100;
                               agent_data(10) = row_pos;
                               agent_data(11) = col_pos;
                               agent_data(16) = agent_data(16) + 1; % remains due resource wealth ;
                           else
                           %agent can afford to move
                               agent_data(9) = -100;
                               agent_data(10) = row_pos;
                               agent_data(11) = col_pos;
                               agent_data(14) = agent_data(14) + 1; %relocations due to wealth
                           end 
                       elseif okay_to_move <= resource_threshold
                           %if agent cant afford to move but there are
                           %still resources left. here agents just above
                           %their resource thresholds.
                             if resource_pos > 2 
                                agent_data(7) = 1; %if their are resouces then work
                                agent_data(9) = 100;
                                agent_data(10) = row_pos;
                                agent_data(11) = col_pos;
                                 agent_data(15) = agent_data(15) + 1; % remains due resource poverty ;
                             else
                                 %just move and hope yah find a better spot
                                 agent_data(9) = -100;
                                 agent_data(10) = row_pos;
                                 agent_data(11) = col_pos;
                                 agent_data(13) = agent_data(13) + 1; % relocations due resource poverty ;
                             end 
                       end
                       
           elseif available_resources <= resource_threshold && percent_same < tolerance
                        if resource_pos > 2 
                            agent_data(7) = 1; %if their are resouces then work
                            agent_data(9) = 100;
                            agent_data(10) = row_pos;
                            agent_data(11) = col_pos;
                            agent_data(15) = agent_data(15) + 1; % remains due resource poverty ;
                        else
                                 %just 9move and hope yah find a better spot
                            agent_data(9) = -100;
                            agent_data(10) = row_pos;
                            agent_data(11) = col_pos;
                            agent_data(13) = agent_data(13) + 1; % relocations due resource poverty ;
                       
                        end 
            end 
       
            agent_data(3) = reset_tol;
            AGENT_DYNAMICS(:, d) = agent_data;
        end 
    end 
end 

mean_segregation = (mean(AGENT_DYNAMICS(4, :)))*100;
[ro1, co1] = find(AGENT_DYNAMICS(9, :)==100);
percent_satisfied = (length(ro1)/length(AGENT_DYNAMICS(9, :)))*100;