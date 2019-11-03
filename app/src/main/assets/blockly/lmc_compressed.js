'use strict';

Blockly.LMC = new Blockly.Generator("LMC");

Blockly.Blocks['move_right'] = {
  init: function() {
    this.appendValueInput("PIXELS")
        .setCheck("Number")
        .appendField("move to right");
    this.setInputsInline(true);
    this.setOutput(true, null);
    this.setColour(120);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.LMC['lda']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code="1"+addr;
    return code;
}