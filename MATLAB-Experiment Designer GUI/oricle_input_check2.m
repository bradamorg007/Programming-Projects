    function oricle_input_check2(varargin)
    %Checks That dependent experiment settings field all corrispond
    %correctly

 p = inputParser;
  defaultx = [];
  defaulty = [];
  defaultz = [];
  addParameter(p,'x', defaultx);
  addParameter(p, 'y', defaulty);
  addParameter(p, 'z', defaultz);
  
  
 parse(p,varargin{:});
 
 x = p.Results.x;
 y = p.Results.y;
 z = p.Results.z;
 NumofInputs = length(varargin)/2;
 
 
 switch NumofInputs
     
     case 2
         
         xlen = length(x);
         ylen = length(y);
         
        if isequal(xlen, ylen) ~= 1
        msg = 'ERROR: The Lengths of the following vectors may not be equal: "Probe Percentage" & "Trials per Memory load" or "Mixed data type ratio & Items per Memory Load"';
        msgbox(msg)
        error(msg) 
        end 
     case 3
        
    ylen = length(y);
    zlen = length(z);

    if isequal(x, ylen, zlen) ~= 1
        msg = 'ERROR: The Lengths of vectors - Memory load, Items per Memory load, Trials per memory load are not equal';
        msgbox(msg)
        error(msg)
    end 
 end 
    end 