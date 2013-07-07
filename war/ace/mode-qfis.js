ace.define('ace/mode/qfis', ['require', 'exports', 'module' , 'ace/lib/oop', 'ace/mode/text', 'ace/tokenizer', 'ace/mode/qfis_highlight_rules', 'ace/range'], function(require, exports, module) {
"use strict";
var oop = require("../lib/oop");
var TextMode = require("./text").Mode;
var Tokenizer = require("../tokenizer").Tokenizer;
var qfisHighlightRules = require("./qfis_highlight_rules").qfisHighlightRules;
var Range = require("../range").Range;

var Mode = function() {
    this.$tokenizer = new Tokenizer(new qfisHighlightRules().getRules());
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
        "axiom": 1,
        "do": 1,
        "else": 1,
        "ensure": 1,
        "function": 1,
        "if": 1,
        "invariant": 1,
        "local": 1,
        "loop": 1,
        "modify": 1,
        "routine": 1,
        "require": 1,
        "then": 1
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

ace.define('ace/mode/qfis_highlight_rules', ['require', 'exports', 'module' , 'ace/lib/oop', 'ace/lib/lang', 'ace/mode/text'], function(require, exports, module) {
"use strict";

var oop = require("../lib/oop");
var lang = require("../lib/lang");
var TextHighlightRules = require("./text_highlight_rules").TextHighlightRules;

var qfisHighlightRules = function() {

    // taken from http://

    var keywords = lang.arrayToMap(
        ("axiom|forall|exist|function|"+
        "old|"+
        "skip|assert|assume|havoc|split|into|"+
        "if|then|else|end|"+
        "until|invariant|loop|end|"+
        "call|in|"+
        "routine|require|ensure|modify|local|do|"+
        "global").split("|")
    );

    var builtInTypes = lang.arrayToMap(
        ("INTEGER|SEQUENCE|BOOLEAN").split("|")
    );

    var operators = lang.arrayToMap(
        (":=|\\+|\\-|\\*|\\/|=|\\/=|<|<=|>|>=|==|=\\/=|"+
			"and|or|not|==>|<==>").split("|")
    );

    // regexp must not have capturing parentheses. Use (?:) instead.
    // regexps are ordered -> the first match is used

    this.$rules = {
        "start" : [
            {
                token : "comment",
                regex : "\\-\\-.*$"
            }, {
                token : "string", // assertion tag
                regex : '[_][0-9a-zA-Z_]+[:]'
            }, {
                token : "constant.numeric", // hex
                regex : "0[xX][0-9a-fA-F]+\\b"
            }, {
                token : "constant.numeric", // float
                regex : "[+-]?\\d+(?:(?:\\.\\d*)?(?:[eE][+-]?\\d+)?)?\\b"
            }, {
                token : "constant.language.boolean",
                regex : "(?:True|False|e)\\b"
            }, {
                token : function(value) {
                    if (value == "current" || value == "Current")
                        return "variable.language";
                    else if (keywords.hasOwnProperty(value))
                        return "keyword";
                    else if (builtInTypes.hasOwnProperty(value))
                        return "constant.language";
                    else
                        return "identifier";
                },
                regex : "[a-zA-Z_$][a-zA-Z0-9_$]*\\b"
            }, {
                token : "keyword.operator",
                regex : ":=|\\+|\\-|\\*|\\/|=|\\/=|<|<=|>|>=|==|=\\/=|==>|<==>(?:and|or|not)"
            }, {
                token : "lparen",
                regex : "[[(]"
            }, {
                token : "rparen",
                regex : "[\\])]"
            }, {
                token : "text",
                regex : "\\s+"
            }, {
                token : "support.constant",
                regex : "%."
            } 
        ],
    };
    
};

oop.inherits(qfisHighlightRules, TextHighlightRules);

exports.qfisHighlightRules = qfisHighlightRules;
});