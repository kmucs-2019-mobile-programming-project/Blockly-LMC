'use strict';

Blockly.LMC = new Blockly.Generator("LMC");

Blockly.Blocks['add'] = {
    init: function () {
        this.appendDummyInput()
            .appendField("ADD")
            .appendField(new Blockly.FieldNumber(0, 0, 99), "ADDR");
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(0);
    }
};

Blockly.Blocks['sub'] = {
    init: function () {
        this.appendDummyInput()
            .appendField("SUB")
            .appendField(new Blockly.FieldNumber(0, 0, 99), "ADDR");
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(30);
    }
};

Blockly.Blocks['sta'] = {
    init: function () {
        this.appendDummyInput()
            .appendField("STA")
            .appendField(new Blockly.FieldNumber(0, 0, 99), "ADDR");
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(60);
    }
};

Blockly.Blocks['lda'] = {
    init: function () {
        this.appendDummyInput()
            .appendField("LDA")
            .appendField(new Blockly.FieldNumber(0, 0, 99), "ADDR");
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(90);
    }
};

Blockly.Blocks['jmp'] = {
    init: function () {
        this.appendDummyInput()
            .appendField("JMP")
            .appendField(new Blockly.FieldNumber(0, 0, 99), "ADDR");
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(120);
    }
};

Blockly.Blocks['jez'] = {
    init: function () {
        this.appendDummyInput()
            .appendField("JEZ")
            .appendField(new Blockly.FieldNumber(0, 0, 99), "ADDR");
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(150);
    }
};

Blockly.Blocks['jnz'] = {
    init: function () {
        this.appendDummyInput()
            .appendField("JNZ")
            .appendField(new Blockly.FieldNumber(0, 0, 99), "ADDR");
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(180);
    }
};

Blockly.Blocks['inp'] = {
    init: function () {
        this.appendDummyInput()
            .appendField("INP")
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(210);
    }
};

Blockly.Blocks['out'] = {
    init: function () {
        this.appendDummyInput()
            .appendField("OUT")
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(240);
    }
};

Blockly.Blocks['cob'] = {
    init: function () {
        this.appendDummyInput()
            .appendField("COB")
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(270);
    }
};

Blockly.Blocks['nop'] = {
    init: function () {
        this.appendDummyInput()
            .appendField("NOP")
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(300);
    }
};

Blockly.Blocks['dat'] = {
    init: function () {
        this.appendDummyInput()
            .appendField("DAT")
            .appendField(new Blockly.FieldNumber(0, 0, 999), "DATA");
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(330);
    }
};


Blockly.LMC['add']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "1" + zeropad(addr, 2);
    var nextBlock = block.nextConnection && block.nextConnection.targetBlock();
    if(nextBlock != null){
        var nextCode = Blockly.LMC.blockToCode(nextBlock);
        code += ' ' + nextCode;
    }
    return code;
}

Blockly.LMC['sub']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "2" + zeropad(addr, 2);
    var nextBlock = block.nextConnection && block.nextConnection.targetBlock();
    if(nextBlock != null){
        var nextCode = Blockly.LMC.blockToCode(nextBlock);
        code += ' ' + nextCode;
    }
    return code;
}

Blockly.LMC['sta']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "3" + zeropad(addr, 2);
    var nextBlock = block.nextConnection && block.nextConnection.targetBlock();
    if(nextBlock != null){
        var nextCode = Blockly.LMC.blockToCode(nextBlock);
        code += ' ' + nextCode;
    }
    return code;
}

Blockly.LMC['lda']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "4" + zeropad(addr, 2);
    var nextBlock = block.nextConnection && block.nextConnection.targetBlock();
    if(nextBlock != null){
        var nextCode = Blockly.LMC.blockToCode(nextBlock);
        code += ' ' + nextCode;
    }
    return code;
}

Blockly.LMC['jmp']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "5" + zeropad(addr, 2);
    var nextBlock = block.nextConnection && block.nextConnection.targetBlock();
    if(nextBlock != null){
        var nextCode = Blockly.LMC.blockToCode(nextBlock);
        code += ' ' + nextCode;
    }
    return code;
}

Blockly.LMC['jez']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "6" + zeropad(addr, 2);
    var nextBlock = block.nextConnection && block.nextConnection.targetBlock();
    if(nextBlock != null){
        var nextCode = Blockly.LMC.blockToCode(nextBlock);
        code += ' ' + nextCode;
    }
    return code;
}

Blockly.LMC['jnz']=function(block)
{
    var addr = block.getFieldValue('ADDR');
    var code = "7" + zeropad(addr, 2);
    var nextBlock = block.nextConnection && block.nextConnection.targetBlock();
    if(nextBlock != null){
        var nextCode = Blockly.LMC.blockToCode(nextBlock);
        code += ' ' + nextCode;
    }
    return code;
}

Blockly.LMC['inp']=function(block)
{
    var code = "801";
    var nextBlock = block.nextConnection && block.nextConnection.targetBlock();
    if(nextBlock != null){
        var nextCode = Blockly.LMC.blockToCode(nextBlock);
        code += ' ' + nextCode;
    }
    return code;
}

Blockly.LMC['out']=function(block)
{
    var code = "802";
    var nextBlock = block.nextConnection && block.nextConnection.targetBlock();
    if(nextBlock != null){
        var nextCode = Blockly.LMC.blockToCode(nextBlock);
        code += ' ' + nextCode;
    }
    return code;
}

Blockly.LMC['cob']=function(block)
{
    var code = "900";
    var nextBlock = block.nextConnection && block.nextConnection.targetBlock();
    if(nextBlock != null){
        var nextCode = Blockly.LMC.blockToCode(nextBlock);
        code += ' ' + nextCode;
    }
    return code;
}

Blockly.LMC['nop']=function(block)
{
    var code = "000";
    var nextBlock = block.nextConnection && block.nextConnection.targetBlock();
    if(nextBlock != null){
        var nextCode = Blockly.LMC.blockToCode(nextBlock);
        code += ' ' + nextCode;
    }
    return code;
}

Blockly.LMC['dat']=function(block)
{
    var addr = block.getFieldValue('DATA');
    var code = zeropad(addr, 3);
    var nextBlock = block.nextConnection && block.nextConnection.targetBlock();
    if(nextBlock != null){
        var nextCode = Blockly.LMC.blockToCode(nextBlock);
        code += ' ' + nextCode;
    }
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
