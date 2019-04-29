function new_world = MoveAgents(relocation_list, world)
[num_agents, ~] = size(relocation_list);

for i = 1:num_agents
[available_loc_rows, available_loc_cols] = find(world == 0);
available_loc = [available_loc_rows, available_loc_cols];
len = length(available_loc);
agent = relocation_list(i, 2);

if isequal(agent, -100)
    %agent wants to move
    %find agents group and current location
    agents_group = world(relocation_list(i, 3), relocation_list(i, 4));
    new_pos = available_loc(randi([1, len], 1), :);
    
    %update world state with agents new position
    world(relocation_list(i, 3), relocation_list(i, 4)) = 0;
    world(new_pos(1), new_pos(2)) = agents_group;
    
end 

end 
new_world = world;
end 

