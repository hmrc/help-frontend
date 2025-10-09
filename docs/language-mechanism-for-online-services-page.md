# Language mechanism for Online Services page

Recently we introduced a global filter which looks for lang url query parameters. However, we also have a maybeChangeLang method in the HelpController. 

There's a subtle distinction between the way maybeChangeLang and LanguageChangeFilter works.

The global filter will only look for `lang=en|cy` url query parameters (case-insensitive). The Online Services page however will allow a different language ISO standard (`en-GB`, for example).

## For Developers

If you're looking at whether or not to remove the maybeChangeLang method in the HelpController, consider whether the LanguageChangeFilter can be improved to achieve the same results as the maybeChangeLang method.
