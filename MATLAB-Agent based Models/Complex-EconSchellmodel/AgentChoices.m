function[gridworld, resourceworld,Id_grid,AGENT_DYNAMICS, death] = AgentChoices(AGENT_DYNAMICS, gridworld,...
                                                                                resourceworld, Id_grid, maxpay, relocation_cost)
 death = 0; 
 
 % This function performs all the agents choices, it pays works, manages
 % the resource space in accordance to what has been mined by agents.
 % Agents exchange resource points to upkeep survival meter. Agents are
 % moved, or remain or initlised into work. Also agents are assesed for
 % being broke or dead dependning on how morbidly you want to interpret it
 % ahah. 
 
[~, num_agents] = size(AGENT_DYNAMICS);


for i = 1:num_agents
    
if ~isequal(AGENT_DYNAMICS(12, i), 1)
[available_loc_rows, available_loc_cols] = find(gridworld == 0);
available_loc = [available_loc_rows, available_loc_cols];
len = length(available_loc);
movestat = AGENT_DYNAMICS(9, i);
workstat = AGENT_DYNAMICS(7, i);
resourcestat = AGENT_DYNAMICS(6, i);
survivalstat = AGENT_DYNAMICS(5, i);
groupstat = AGENT_DYNAMICS(2, i);
idstat = AGENT_DYNAMICS(1, i);

agent_location = [AGENT_DYNAMICS(10, i), AGENT_DYNAMICS(11, i)];

if resourcestat > 2
    resourcestat = resourcestat - 2;
    survivalstat = survivalstat + 2;
end

if resourcestat < 0 && resourcestat <= relocation_cost && movestat==-100
    movestat = 100;
    %They  had one chance to move but it failed so they are stuck
end 

if isequal(movestat, 100)
    if isequal(workstat, 1)
    pay = randi([1, maxpay], 1);
    if resourceworld(agent_location(1),agent_location(2)) > maxpay
    resourceworld(agent_location(1),agent_location(2)) = resourceworld(agent_location(1),agent_location(2)) - pay;
    resourcestat = resourcestat + pay;  
    else 
        resourceworld(agent_location(1),agent_location(2)) = 0;
    end
     
    end 
    
    if  survivalstat <= 0
    %agent death
    AGENT_DYNAMICS(12, i) = 1;
    if isequal(agent_location(1), 0) && isequal(agent_location(2), 0)
    break
    end
    gridworld(agent_location(1),agent_location(2)) = 0;
    Id_grid(agent_location(1),agent_location(2))= 0;
    death = death + 1;
    
    end 
    
elseif isequal(movestat, -100)
    %agent wants to move
    %find agents group and current location
    
    resourcestat = resourcestat - relocation_cost;
    new_pos = available_loc(randi([1, len], 1), :);
    
    %update world state with agents new position
    gridworld(agent_location(1),agent_location(2)) = 0;
    if isequal(Id_grid(agent_location(1),agent_location(2)), idstat)
       Id_grid(agent_location(1),agent_location(2)) = 0;
    end
    gridworld(new_pos(1), new_pos(2)) = groupstat;
    Id_grid(new_pos(1), new_pos(2)) = idstat;
    
    AGENT_DYNAMICS(10, i) = new_pos(1);
    AGENT_DYNAMICS(11, i) = new_pos(2);
    if  survivalstat <= 0
    %agent death
    AGENT_DYNAMICS(12, i) = 1;
    gridworld(new_pos(1),new_pos(2)) = 0;
    Id_grid(new_pos(1),new_pos(2) )= 0;
    death = death + 1;
   
    end 
end 

    AGENT_DYNAMICS(6, i) = resourcestat;
    AGENT_DYNAMICS(5, i) = survivalstat;
    

end 

end 


end 

