$(document).on('ready', function () {
    var send = false;
	$('#encrypt-form').submit(function (e) {
		e.preventDefault();
        var msg = $('#considered-text').val();;
        var key = $('#key').val();
        algorithm = $('#algorithm').val().toUpperCase();
        if (algorithm === "CAESAR") {
            caesar(msg,key);
        } else if (algorithm === "VIGENERE") {
            vigenere(msg,key);
        } else {
          send = true;
        }
        console.log(send);
        if (send) {
            $.post( 'Encrypt', $('#encrypt-form').serialize(), function (data) {
                $('#output').html(data);
            }).fail(function () {
                $('#output').text('Ocurrió un error inesperado, intente de nuevo más tarde');
            });
        };
	});

    function caesar(msg,key){
        var cleanMsg = leaveJustLetters(msg);
        $('#considered-text').val(cleanMsg);
        if (isNaN(key)) {
            $('#key').val(key.charAt(0).toUpperCase());
        }
        send = true;
    }
    function vigenere(msg,key){
        var cleanMsg = leaveJustLetters(msg);
        $('#considered-text').val(cleanMsg);
        var cleanKey = leaveJustLetters(key);
        $('#key').val(cleanKey);
    }
    function leaveJustLetters(msg){
        var msg_validation = /[A-Z]/;
        var msg_aux = [];
        var ii = 0;
        msg = msg.toUpperCase();
        for (var i = 0; i < msg.length; i++) {
            if (msg_validation.test(msg[i])) {
                msg_aux[ii] = msg[i];
                ii++;
            }
        }
        msg = msg_aux.join("");
        return msg;
    }
});
