

fig  = figure;
set(fig, 'WindowKeyPressFcn', @KeyPress);

function [UserResponse] = KeyPress(Source, EventData)
disp(EventData.Character)

response = EventData.Character;

if strcmp( response,'n') == 1
        UserResponse = 0;
elseif strcmp(response, 'y') == 1
        UserResponse = 1;
else 
        UserResponse = 2;
end 
        

end 