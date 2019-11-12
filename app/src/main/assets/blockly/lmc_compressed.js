'use strict';

Blockly.LMC = new Blockly.Generator("LMC");

Blockly.Blocks['lda'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("LDA")
        .appendField(new Blockly.FieldNumber(0, 0, 99), "ADDR");
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(230);
    this.setTooltip("");
    this.setHelpUrl("");
  }
};

Blockly.LMC['add']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "1" + zeropad(addr, 2);
    return code;
}

Blockly.LMC['sub']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "2" + zeropad(addr, 2);
    return code;
}

Blockly.LMC['sta']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "3" + zeropad(addr, 2);
    return code;
}

Blockly.LMC['lda']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "4" + zeropad(addr, 2);
    return code;
}

Blockly.LMC['jmp']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "5" + zeropad(addr, 2);
    return code;
}

Blockly.LMC['jez']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "6" + zeropad(addr, 2);
    return code;
}

Blockly.LMC['jnz']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "7" + zeropad(addr, 2);
    return code;
}

Blockly.LMC['inp']=function(block)
{
    var code = "801";
    return code;
}

Blockly.LMC['out']=function(block)
{
    var code = "802";
    return code;
}

Blockly.LMC['cob']=function(block)
{
    var code = "900";
    return code;
}

Blockly.LMC['dat']=function(block)
{
    var addr = block.getFieldValue('DATA');
    var code = zeropad(addr, 3);
    return code;
}

function zeropad(num, digit)
{
    num = num + '';
    while(num.length < digit){
        num = '0' + num;
    }
    return num;
}

function generateCode()
{
    try{
        window.AndroidAPP.getCode(Blockly.LMC.workspaceToCode(workspacePlayground));
    }catch(err){
        console.log("[generateCode()]: " + err);
    }
}
