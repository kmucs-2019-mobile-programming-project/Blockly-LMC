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

Blockly.LMC['lda']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code="1"+addr;
    return code;
}

function generateCode()
{
    try{
        window.AndroidAPP.getCode(Blockly.LMC.workspaceToCode(workspacePlayground));
    }catch(err){
        console.log("[generateCode()]: " + err);
    }
}
