ace.define('ace/mode/synquid', ['require', 'exports', 'module' , 'ace/lib/oop', 'ace/mode/text', 'ace/tokenizer', 'ace/mode/synquid_highlight_rules', 'ace/range'], function(require, exports, module) {


var oop = require("../lib/oop");
var TextMode = require("./text").Mode;
var Tokenizer = require("../tokenizer").Tokenizer;
var synquidHighlightRules = require("./synquid_highlight_rules").synquidHighlightRules;
var Range = require("../range").Range;

var Mode = function() {
    this.$tokenizer = new Tokenizer(new synquidHighlightRules().getRules());
};

oop.inherits(Mode, TextMode);

(function() {
    
    this.toggleCommentLines = function(state, doc, startRow, endRow) {
        var outdent = true;
        var re = /^(\s*)--/;

        for (var i=startRow; i<= endRow; i++) {
            if (!re.test(doc.getLine(i))) {
                outdent = false;
                break;
            }
        }

        if (outdent) {
            var deleteRange = new Range(0, 0, 0, 0);
            for (var i=startRow; i<= endRow; i++)
            {
                var line = doc.getLine(i);
                var m = line.match(re);
                deleteRange.start.row = i;
                deleteRange.end.row = i;
                deleteRange.end.column = m[0].length;
                doc.replace(deleteRange, m[1]);
            }
        }
        else {
            doc.indentRows(startRow, endRow, "--");
        }
    };

    var indentKeywords = {
        "else": 1,
        "if": 1,
        "then": 1,
        "where": 1
    }

    this.getNextLineIndent = function(state, line, tab) {
        var indent = this.$getIndent(line);

        var tokenizedLine = this.$tokenizer.getLineTokens(line, state);
        var tokens = tokenizedLine.tokens;

        var firstToken = tokens[0];

        do {
            var last = tokens.pop();
        } while (last && (last.type == "comment" || (last.type == "text" && last.value.match(/^\s*$/))));

        if (last) {
            if (indentKeywords[last.value]) {
                indent += tab;
            }
        }

        return indent;
    };

}).call(Mode.prototype);

exports.Mode = Mode;
});

ace.define('ace/mode/synquid_highlight_rules', ['require', 'exports', 'module' , 'ace/lib/oop', 'ace/lib/lang', 'ace/mode/text'], function(require, exports, module) {

var oop = require("../lib/oop");
var lang = require("../lib/lang");
var TextHighlightRules = require("./text_highlight_rules").TextHighlightRules;

var synquidHighlightRules = function() {

    // taken from http://

    var keywords = lang.arrayToMap(
        ("data|else|if|in|let|match|measure|predicate|qualifier|termination|then|type|with|where").split("|")
    );

    var builtInTypes = lang.arrayToMap(
        ("Bool|Int|Set").split("|")
    );

    var operators = lang.arrayToMap(
      ("*|+|-|==|!=|<|<=|>|>=|&&|\|\||==>|<==>"+
      "::|:|->|\||=|??|,|.|\\").split("|")
    );

    // regexp must not have capturing parentheses. Use (?:) instead.
    // regexps are ordered -> the first match is used

    this.$rules = {
        "start" : [
            {
                token : "comment",
                regex : /\-\-.*$/
            },
            {
                token : "constant.language.boolean",
                regex : /(?:True|False|e)\b/
            },
            {
                token : function(value) {
                    if (value == "_v")
                        return "variable.language";
                    else if (keywords.hasOwnProperty(value))
                        return "keyword";
                    else if (builtInTypes.hasOwnProperty(value))
                        return "constant.language";
                    else
                        return "identifier";
                },
                regex : /[a-zA-Z_'$][a-zA-Z0-9_'$]*\b/
            },
            {
                token : "keyword.operator",
                regex : /\*|\+|-|==|!=|<|<=|>|>=|&&|\|\||==>|<==>|::|:|->|\||=|\?\?|,|.|\\/
            },
            {
                token : "lparen",
                regex : /[[(]/
            },
            {
                token : "rparen",
                regex : /[\])]/
            },
            {
                token : "text",
                regex : /\s+/
            }
        ],
    };
    
};

oop.inherits(synquidHighlightRules, TextHighlightRules);

exports.synquidHighlightRules = synquidHighlightRules;
});
