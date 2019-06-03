// Carregar os ingredientes adicionais e sua respectiva quantidade
$(document).ready(function() {
	$('#alface').change(function(){
        if($("#alface").is(":checked")){
            $("#child2").append("<select id='qtdeAlface'></select>");
            $("#qtdeAlface").append("<option value='selecione' selected='selected'>Quantidade</option>");
            $("#qtdeAlface").append("<option value='1'>1x Alface</option>");
            $("#qtdeAlface").append("<option value='2'>2x Alface</option>");
            $("#qtdeAlface").append("<option value='3'>3x Alface</option>");
            $("#qtdeAlface").append("<option value='4'>4x Alface</option>");
            $("#qtdeAlface").append("<option value='5'>5x Alface</option>");
            $("#qtdeAlface").append("<option value='6'>6x Alface</option>");
			$("#qtdeAlface").append("<option value='7'>7x Alface</option>");
			$("#qtdeAlface").append("<option value='8'>8x Alface</option>");
			$("#qtdeAlface").append("<option value='9'>9x Alface</option>");
			$("#qtdeAlface").append("<option value='10'>10x Alface</option>");
        } else {
            $("#qtdAlface").remove();
        }
    });
    $('#bacon').change(function(){
        if($("#bacon").is(":checked")){
            $("#child2").append("<select id='qtdeBacon'></select>");
            $("#qtdeBacon").append("<option value='selecione' selected='selected'>Quantidade</option>");
            $("#qtdeBacon").append("<option value='1'>1x Bacon</option>");
            $("#qtdeBacon").append("<option value='2'>2x Bacon</option>");
            $("#qtdeBacon").append("<option value='3'>3x Bacon</option>");
            $("#qtdeBacon").append("<option value='4'>4x Bacon</option>");
            $("#qtdeBacon").append("<option value='5'>5x Bacon</option>");
            $("#qtdeBacon").append("<option value='6'>6x Bacon</option>");
			$("#qtdeBacon").append("<option value='7'>7x Bacon</option>");
			$("#qtdeBacon").append("<option value='8'>8x Bacon</option>");
			$("#qtdeBacon").append("<option value='9'>9x Bacon</option>");
			$("#qtdeBacon").append("<option value='10'>10x Bacon</option>");
        } else {
            $("#qtdeBacon").remove();
        }
    });
    $('#burger').change(function(){
        if($("#burger").is(":checked")){
            $("#child2").append("<select id='qtdeBurger'></select>");
            $("#qtdeBurger").append("<option value='selecione' selected='selected'>Quantidade</option>");
            $("#qtdeBurger").append("<option value='1'>1x Hambúrguer de carne</option>");
            $("#qtdeBurger").append("<option value='2'>2x Hambúrguer de carne</option>");
            $("#qtdeBurger").append("<option value='3'>3x Hambúrguer de carne</option>");
            $("#qtdeBurger").append("<option value='4'>4x Hambúrguer de carne</option>");
            $("#qtdeBurger").append("<option value='5'>5x Hambúrguer de carne</option>");
            $("#qtdeBurger").append("<option value='6'>6x Hambúrguer de carne</option>");
			$("#qtdeBurger").append("<option value='7'>7x Hambúrguer de carne</option>");
			$("#qtdeBurger").append("<option value='8'>8x Hambúrguer de carne</option>");
			$("#qtdeBurger").append("<option value='9'>9x Hambúrguer de carne</option>");
			$("#qtdeBurger").append("<option value='10'>10x Hambúrguer de carne</option>");
        } else {
            $("#qtdeBurger").remove();
        }
    });
    $('#ovo').change(function(){
        if($("#ovo").is(":checked")){
            $("#child2").append("<select id='qtdeOvo'></select>");
            $("#qtdeOvo").append("<option value='selecione' selected='selected'>Quantidade</option>");
            $("#qtdeOvo").append("<option value='1'>1x Ovo</option>");
            $("#qtdeOvo").append("<option value='2'>2x Ovo</option>");
            $("#qtdeOvo").append("<option value='3'>3x Ovo</option>");
            $("#qtdeOvo").append("<option value='4'>4x Ovo</option>");
            $("#qtdeOvo").append("<option value='5'>5x Ovo</option>");
            $("#qtdeOvo").append("<option value='6'>6x Ovo</option>");
			$("#qtdeOvo").append("<option value='7'>7x Ovo</option>");
			$("#qtdeOvo").append("<option value='8'>8x Ovo</option>");
			$("#qtdeOvo").append("<option value='9'>9x Ovo</option>");
			$("#qtdeOvo").append("<option value='10'>10x Ovo</option>");
        } else {
            $("#qtdOvo").remove();
        }
    });
    $('#queijo').change(function(){
        if($("#queijo").is(":checked")){
            $("#child2").append("<select id='qtdeQueijo'></select>");
            $("#qtdeQueijo").append("<option value='selecione' selected='selected'>Quantidade</option>");
            $("#qtdeQueijo").append("<option value='1'>1x Queijo</option>");
            $("#qtdeQueijo").append("<option value='2'>2x Queijo</option>");
            $("#qtdeQueijo").append("<option value='3'>3x Queijo</option>");
            $("#qtdeQueijo").append("<option value='4'>4x Queijo</option>");
            $("#qtdeQueijo").append("<option value='5'>5x Queijo</option>");
            $("#qtdeQueijo").append("<option value='6'>6x Queijo</option>");
			$("#qtdeQueijo").append("<option value='7'>7x Queijo</option>");
			$("#qtdeQueijo").append("<option value='8'>8x Queijo</option>");
			$("#qtdeQueijo").append("<option value='9'>9x Queijo</option>");
			$("#qtdeQueijo").append("<option value='10'>10x Queijo</option>");
        } else {
            $("#qtdeQueijo").remove();
        }
    });
	
   // Declaração do lanche selecionado
   var lancheEscolhido;
   
   /**
   * Função responsável por calcular o valor do lanche escolhido
   */
   $("#sel1").change(function(){
      // obtendo o valor do atributo value da tag option
      lancheEscolhido = $("#sel1 option:selected").val();

    	$.ajax({ 
   			type: "GET",
   			url: "http://localhost:8080/WebService/api/calcular-lanche/" + lancheEscolhido,
   			dataType: 'json',
   			success: function(data){        
     			$("#totalPedido").text(data.valor);
   			}
		});
   });

   /**
    * Função responsável por parsear o json para envio no corpo da requisição
	* @param body
    */
    calcularPedido = function (){
		var body = {};
    	var jsonObject = [];
		lancheEscolhido = $("#sel1 option:selected").val();
		body["lanche"] = lancheEscolhido;
    	if($("#alface").is(":checked")){
    		var item = {};
           	item["ingrediente"] = "ALFACE";
           	item["qtde"] = parseInt($("#qtdeAlface option:selected").val());
           	jsonObject.push(item);
    	}
    	if($("#bacon").is(":checked")){
    		var item = {};
           	item["ingrediente"] = "BACON";
           	item["qtde"] = parseInt($("#qtdeBacon option:selected").val());
           	jsonObject.push(item);
    	}
    	if($("#burger").is(":checked")){
    		var item = {};
           	item["ingrediente"] = "BURGER";
           	item["qtde"] = parseInt($("#qtdeBurger option:selected").val());
           	jsonObject.push(item);
    	}
    	if($("#ovo").is(":checked")){
    		var item = {};
           	item["ingrediente"] = "OVO";
           	item["qtde"] = parseInt($("#qtdeOvo option:selected").val());
           	jsonObject.push(item);
    	}
    	if($("#queijo").is(":checked")){
    		var item = {};
           	item["ingrediente"] = "QUEIJO";
           	item["qtde"] = parseInt($("#qtdeQueijo option:selected").val());
           	jsonObject.push(item);
    	}
		body["adicionais"] = jsonObject;
    	callService(body);
    }

	/**
    * Função responsável por calcular o valor total do pedido
	* @param body
    */
    callService = function(body){
    	$.ajax({
        	url: "http://localhost:8080/WebService/api/calcular-pedido",
        	type: "POST",
        	dataType: 'json',
			data: JSON.stringify(body),
        	contentType: "application/json",
         	success: function(data,status){
            	  $("#totalPedido").text(data.total);
         	},
         	error: function(jqXHR, status, errorThrown){
         		 console.log(jqXHR);
            	 alert("Ops... Não foi possível calcular o total do pedido - " + status.code)
         	}
    	});
    }
});