var remoteUrl = "../api/"; 	//using phonegap, it would be nice to be able to do use an absolute url, like: "http://192.168.205.1:9898/api/";
var stackMedias = new Array();
var data;

var currentMediaCategory;
var currentApp;
var $activeApps;

//var lastLiClickPoint;
//var liTapHold;

$( "#home" ).live( "pageinit",function(event){ //$(document).bind('pageinit', function ()  //$(document).ready(function () {
	console.log("#home.live()");
	$.ajaxSetup({
	      "error": function(jqXHR, textStatus, errorThrown) {
	    	  alert( "Error ( " + jqXHR.status + " " + jqXHR.statusText + " ): " + jqXHR.responseText);
	      }
	});
	ajax_getData();	

	$( "#footer a[data-rel != 'dialog']" ).each(function(index) {
		$(this).bind( "vclick", function(event) {
			var id = $(this).attr( "id" ); 
			if (id == 'apps'){
				ajax_getActiveApps(false);
			} else {
				ajax_controlPc(id);										
			}
		});
	});
	
	$('#btn-back').bind( "vclick", function(event) {
		if (stackMedias.length > 1){
			stackMedias.pop();
			displayMedia(stackMedias.pop());
		}
	});
	
	$('a[data-rel="btn-home"]').bind( "vclick", function(event) {
		displayHome();
	});
	
	$('a[data-rel="btn-mouse"],a[data-rel="btn-keyboard"]').bind( "tap", function(event) {
		event.stopImmediatePropagation();
		event.preventDefault();
		var kind = $(this).attr( "data-rel" ).substr(4); //kind is "mouse" or "keyboard"
		displayMouseOrKeyboardRc(kind);
		$.mobile.changePage($( "#" + kind + "control" ));
	});
	
	$( "#keyboard-input" ).bind( "keyup", function(e, ui) {
		if(e.keyCode == 8){
			ajax_keyboardType( "$RES_BACK_SPACE" );
		} else {
			var v = $( "#keyboard-input" ).val();
			if (v.length > 0){
				ajax_keyboardType(v);
				$( "#keyboard-input" ).val( "" );
			}
		}
	});
});

$( "#dialog-pc-poweroff" ).live( "pageinit",function(event){
	$( "#dialog-pc-btn-poweroff,#dialog-pc-btn-suspend,#dialog-pc-btn-cancel" ).bind( "vclick", function(event) {
		handlerBtnDialogPc($(this));
	});
});

$( "#dialog-pc-vol" ).live( "pageinit",function(event){
	$( "#dialog-pc-btn-volmute" ).bind( "vclick", function(event) {
		handlerBtnDialogPc($(this));
	});
	$( "#dialog-pc-vol-value" ).bind( "change", function(event) {
		ajax_controlPc( "vol-"+$( "#dialog-pc-vol-value" ).val());
	});
});

function handlerBtnDialogPc(button) {
	var id = button.attr( "id" ).substr(14);
	if (id != "cancel" ){
		ajax_controlPc(id);
	}
	$.mobile.changePage($('#home'));
}

function ajax_getData() {
	$.getJSON(remoteUrl + 'data', function(d) {
		data = d;
		$( "#titleSection" ).html(data.settings.nameRoot);
		displayMediaRoots(data.mediaRoots);
	});
}

function ajax_getMediaRoots() {
	$.getJSON(remoteUrl + "mediaroots", function(mediaRoots) {
		displayMediaRoots(mediaRoots);
	});
}

function ajax_getMedias(media, $liCount) {
	$.ajax({
		type: "GET",
		url:   
			remoteUrl + "medias" 
			+ (currentMediaCategory ? ("/"+currentMediaCategory.name) : "" ) 
			+ (media.path ? "?path="+encodeURI(media.path) : ""),
		success: function(data) {
			if (data && data.length){
				media.mediaChildren = data;
				displayMedia(media);
			}
			if ($liCount){
				$liCount.html( "" + data.length);
			}
			media.mediaChildrenSize = data.length;
		}
	});
}

function ajax_openFile(media) {
	$.ajax({
		type: "POST",
		url: remoteUrl + "medias/"+ (currentApp ? currentApp.name : ""),
		dataType: 'json',
		contentType:"application/json; charset=utf-8",
		data: media.path
	});
}

function ajax_control(kind, command) {
	var u;
	if (kind == "keyboard" || kind == "mouse"){
		u = remoteUrl + "pc/" + kind + "/control/" + command;
	} else {
		u = remoteUrl + "apps/"+(currentApp ? currentApp.name + "/" : "" ) + "control/" + command
	}
	$.ajax({
		type: "POST",
		url: u
	});
}

function ajax_controlPc(command) {
	$.ajax({
		type: "POST",
		url: remoteUrl + "pc/control/"+command,
		success: function(data) {
			if (command=="nextapp" ){
				displayMediaRc(getConfiguredApp(data.name)); //data is model ActiveApp
			}
	  	}
	});
}

function ajax_keyboardType(command) {
	$.ajax({
		type: "POST",
		url: remoteUrl + "pc/keyboard",
		dataType: 'json',
		contentType:"application/json; charset=utf-8",
		data: command
	});
}

function ajax_getActiveApps(refresh) {
	$.ajax({
		type: "GET",
		url: remoteUrl + "activeapps?refresh=true",
	  	success: function(data) {
	  		displayApps(data);
	  	}
	});
}

function ajax_focusApp(pid) {
	$.ajax({
		type: "POST",
		url: remoteUrl + "activeapps/"+pid + "/focus"
	});
}

function ajax_killApps(pids) {
	$.ajax({
		type: "DELETE",
		url: remoteUrl + "activeapps",
		dataType: 'json',
		contentType:"application/json; charset=utf-8",
		data: JSON.stringify(pids)
	});
}

function displayHome(){
	$.mobile.changePage($('#home'), { changeHash: false});
	displayMediaRoots(data.mediaRoots);
}

function displayMediaRoots(mediaRoots){
	stackMedias = new Array();
	displayMedia({
		name: data.settings.name,
		mediaChildren: mediaRoots
	});
}

function displayMedia(media){
	stackMedias.push(media);
	if (media.mediaCategory){
		currentMediaCategory = media.mediaCategory;
		displayMediaRc(currentMediaCategory.app);
	}
	var $content = $( "#home div[data-role='content']" );
	var $ul = $( "#listMedia"); 
	if ($ul.length == 0){	//Let's created it the first time
		var $divMediaTitle = $( "<div>" + //TODO btn disalign
								"<a data-role='button' href='#' data-iconpos='notext' data-icon='refresh' " +
										"data-inline='true' data-mini='true' id='btn-refresh'></a>" +
								"<h4 id='media-title'></h4>" +
							"</div>" ).appendTo($content);
		$divMediaTitle.trigger("create");
		$( "#btn-refresh" ).bind( "vclick", function(event) {
			if (stackMedias.length === 1){
				console.log( "stackMedias.isEmpty(). calling getMediaRoots" );
				ajax_getMediaRoots();
			} else {
				media = stackMedias.pop();
				console.log( "Getting media " + media.path);
				ajax_getMedias(media, null);
			}
		});
	} else {
		$ul.parent().find("form").remove();
		$ul.remove();
	}
	$ul = $( "<ul id='listMedia' data-role='listview' data-filter='true'></ul>" ).appendTo($content);
	$( "#media-title" ).html(media.name);
	if (media.mediaChildren.length){
		$.each(media.mediaChildren, function(i, mediaChild){
			var name = mediaChild.name;
			var img = "<img class='ui-li-icon' src='images/" + (mediaChild.file ? "file" : "dir" ) + ".png'/>";
			if (!mediaChild.file){
				name += "<span class=ui-li-count>"+(mediaChild.mediaChildrenSize > -1 ? mediaChild.mediaChildrenSize : "?" ) +"</span>";
			}
			name = "<a>" + img + name + "</a>";
			var $li = $( "<li>" + name + "</li>" ).appendTo($ul);
			if (mediaChild.mediaChildren && currentApp && currentApp.argumentsDir){
				$li.bind( "taphold", function(event) {
					//liTapHold = true;
					event.stopImmediatePropagation();
					event.preventDefault();
					ajax_openFile(mediaChild);
					//liTapHold = false;
					return false;
				});
			}
			$li.bind( "vclick", function(event) {
				//if (!liTapHold){
//					var curclickpoint = event.clientX+'x'+event.clientY
//					if (lastLiClickPoint == curclickpoint) return false;
//					lastLiClickPoint = curclickpoint;
					if (mediaChild.file){
						ajax_openFile(mediaChild);
					} else if (mediaChild.mediaChildrenSize != 0) {
						//push in history
						//window.history.pushState(null,null,mediaChild.name);
						if (mediaChild.mediaChildrenSize > 0){ 
							displayMedia(mediaChild);
						} else if (mediaChild.mediaChildrenSize == -1){
							ajax_getMedias(mediaChild, $li.find( "span.ui-li-count" ));
						}						
					}
				//}
				//liTapHold = false;
				event.stopImmediatePropagation();
				event.preventDefault();
				return false;
			});
		});
	}
	$ul.listview();
}

function displayMediaRc(app){
	if (app){
		displayRc(
				$( "#home > div" ).eq(0), 
				"rc", 
				app.name, 
				app.controlsManager, 
				"26",
				10);
	} else {
		$( "#rc" ).addClass('hided');	
	}
	currentApp = app;
}

function displayMouseOrKeyboardRc(kind){
	displayRc(
			$( "#" + kind + "control > div" ).eq(1).find( "div" ),	//preeceding brother
			kind, 												//"mouse" or "keyboard"
			"",													//Title
			data[kind+"ControlsManager"], 						//model ControlsManager
			"26",												//Icon size
			10);												//paddingTop
}

//show or hide the div control, depending by the controls defined in controlsManager. 
//$brotherDom preceeding sibling
//idContainer
//title
//controlsManager
//iconSize
//paddingTop
function displayRc($brotherDom, idContainer, title, controlsManager, iconSize, paddingTop){
	var $divRcRows; //Div containing rows of controls
	var $divTitle;
	if ($( "#"+idContainer).length == 0){	//Let's created it the first time
		var $divRc = $( "<div id='" + idContainer + "' class='rc-controls' style='padding-top:" + paddingTop + "px;'></div>" )
			.insertAfter($brotherDom);
		//TODO btns in the div of control
		$divTitle = $( "<div class='rc-title' id='" + idContainer + "_title'>" +
//							"<a class='float-left' data-role='button' href='#' data-iconpos='notext' data-icon='refresh' " +
//								"data-inline='true' data-mini='true' id='btn-hide'></a>" +
							"<span id='" + idContainer + "_title_t'></span>" +
						"</div>" ).appendTo($divRc);
		$divTitle.trigger("create");
//		$("#btn-hide").bind( "vclick", function(event) {
//			console.log( "hide" );
//		});
		$divRcRows = $( "<div id='" + idContainer + "_rows'></div>" ).appendTo($divRc);
	} else {
		$divTitle = $( "#"+idContainer + "_title" );
		$divRcRows = $( "#"+idContainer + "_rows" );	
	}
	$( "#"+idContainer).removeClass('hided');
	if (title.length == 0){
		$divTitle.hide();
	} else {
		$( "#"+idContainer + "_title_t" ).html(title);
		$divTitle.show();
	}
	
	$divRcRows.empty();
	var l=["a","b","c","d","e"];
	for (j=1; j<=4; j++){
		var $row = $( "<div id='"+idContainer+"_row"+j+"' class='ui-grid-d'></div>" ).appendTo($divRcRows);
		for (i=0; i<=4; i++){
			$( "<div align='center' class='ui-block-" + l[i] + " controls-row'></div>" ).appendTo($row);
		}
	}
	
	for (j=0; j<controlsManager.controls.length; j++){
		var control = controlsManager.controls[j];
		var name = control.name;
		var $controlDiv = $( "<div align='center' class='control' id='" + name +"'></div>" )
			.appendTo($( "#"+idContainer+"_row"+control.row + " div.ui-block-"+l[(l.length-1)/2 + control.position]));
		if (control.hideImg){
			$controlDiv.html(control.text);
		} else {
			var $img = $( "<img " 
					+ "src='images/default/" + name + ".png'"
					+ " class='control-img' style='margin-top:-" + iconSize/2 + "px;margin-left:-" + iconSize/2 + "px;'/>" ).appendTo($controlDiv);
			$img.attr( "width", iconSize+"px" );
			$img.attr( "height", iconSize+"px" );			
		}
		$controlDiv.bind( "vmousedown", function(event) {
	    	ajax_control(idContainer, $(this).attr( "id" ))
		});
	}
}

function displayApps(apps){
	if ($( "#btn-killApps" ).length == 0){
		var $dialogActiveApps = $( "#dialog-listActiveApps div[data-role='content']" );
		var $killSel = $( "<a data-role='button' href='#' data-icon='delete' id ='btn-killApps'>kill selected</a>" ).appendTo($dialogActiveApps);
		$killSel.bind( "vclick", function(event) {
			try {
				var handles = new Array();
				$( "#active-apps input:checked" ).each(function(i) {
					handles.push($(this).attr( "id" ).substr(3)); //omit prefix "pid".length
				});
				ajax_killApps(handles);
				$.mobile.changePage( "#home" );
			} catch (e){
				alert(e.message);
			}
		});
		$activeApps = $( "<div id='active-apps' data-role='fieldcontain'></div>" ).appendTo($dialogActiveApps);
	} else {
		$activeApps.empty();
	}
	var $fieldset = $( "<fieldset data-role='controlgroup'></fieldset>" ).appendTo($activeApps);
	$.each(apps, function(i, app){
		$fieldset.append(
				"<input type='checkbox' id='pid" + app.pid + "'></input>" + 
				"<label for='pid" + app.pid + "' class='checkbox-app' data-appname='" + app.name + "'>" +
					"<span data-role='use' class='use-app" + (getConfiguredApp(app.name) ? "" : "-disabled" ) + "'>" + app.name + "</span>" +
					"<span class='checkbox-app-winlbl'>" + app.windowLbl + "</span>" +
				"</label>" );
	});
	$activeApps.find( "span[data-role='use']" ).each(function(i) {
		$(this).bind( "vclick", function(event) {
			event.preventDefault();
			event.stopImmediatePropagation();
			displayHome();
			ajax_focusApp($(this).parents( "label" ).attr( "for" ).substr(3));
			if ($(this).hasClass( "use-app" )){
				displayMediaRc(getConfiguredApp($(this).parents( "label" ).data( "appname" )));
			}
		});
	});
	$fieldset.trigger( "create" ); 
	$.mobile.changePage( "#dialog-listActiveApps" );
}

//
function getConfiguredApp(appName){
	for(j=0; j<data.mediaRoots.length;j++){
		if (data.mediaRoots[j].mediaCategory.app && appName == data.mediaRoots[j].mediaCategory.app.name){
			return data.mediaRoots[j].mediaCategory.app;
		}
	}
	return 0;
}