if(typeof VoteButton == "undefined"){
	var VoteButton = Class.create({
		cssClass: "checked",
		
		initialize: function(checkId, buttonId) {
			this.check = $(checkId); 
			this.button = $(buttonId);

			if(this.check.tagName != "INPUT" || this.check.readAttribute("type") != "checkbox"){
				throw "Check is not <input type='checkbox' />";
			}
			
			this.button.on("click", this.buttonClickHandler.bind(this));

			this.updateStyle();
		},
		
		buttonClickHandler: function(event, element) {
			event.stop();
			
			this.toggle();
			this.updateStyle();
		},

		getInput: function(){
			return this.check.checked;
		},
		
		toggle: function(){
			this.check.click();
		},
		
		updateStyle: function(){
			if(this.getInput()){
				this.button.addClassName(this.cssClass);
			} else {
				this.button.removeClassName(this.cssClass);
			}
		}
	});
}