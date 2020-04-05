[Available placeholders]: https://helpch.at/placeholders

# Progress-Expansion
This is a [PlaceholderAPI](http://placeholderapi.com/) expansion that allows you to display the progress related placeholders based on your inputs.
For bar placeholder, You can customize what symbols are shown, the text when it is completed and what the maximum value is.

## Placeholders
The expansion provides two placeholders that can be customized with additional values/options.
If a option provides a invalid value, then the default one set in the config.yml of PlaceholderAPI is used.

### `%progress_bar_{placeholder}%`

#### `{placeholder}` (**Required field**)
> **Requires**: A placeholder that returns a number OR a letter (A-Z). [Available placeholders] <br />
> **Example**: `%progress_bar_{server_online}%`

The number that the progress bar will based on. For letters, it will be so `a` = 1, `z` = 26

#### `c:`
> **Requires**: `TEXT` <br />
> **Default (Config option)**: `&a■` (`completed`) <br />
> **Example**: `%progress_bar_{server_online}_c:&a|%` <br />

Sets the symbol/text that is used for already completed progress.

#### `p:`
> **Requires**: `TEXT` <br />
> **Default (Config option)**: `&e■` (`in_progress`) <br />
> **Example**: `%progress_bar_{server_online}_p:&e|%` <br />

Sets the symbol/text that is used for the current progress (Where it currently is).

#### `r:`
> **Requires**: `TEXT` <br />
> **Default (Config option)**: `&7■` (`remaining`) <br />
> **Example**: `%progress_bar_{server_online}_r:&7|%` <br />

Sets the symbol/text that is used for the remaining progress.

#### `l:`
> **Requires**: `NUMBER` <br />
> **Default (Config option)**: `10` (`length`) <br />
> **Example**: `%progress_bar_{server_online}_l:5%` <br />

Sets the maximum length of the progress bar. This also affects the progress itself.
For example will a length of `5` show 2/5 as completed while `10` would show 5/10 if the progress is currently 50%.

#### `m:`
> **Requires**: `NUMBER` <br />
> **Default (Config option)**: `100` (`maximum_value`) <br />
> **Example**: `%progress_bar_{server_online}_m:10%` <br />

Sets the maximum value (Goal) of the progress. This affects the progress itself.
For example will `20` show progress 2/10 done while it not even shows 1/10 done with `100` when the current progress is `5`.

#### `fullbar:`
> **Requires**: `TEXT` <br />
> **Default (Config option)**: `&aCompleted` (`full`) <br />
> **Example**: `%progress_bar_{server_online}_fullbar:&aDone!%` <br />

Sets the text that is shown when progress is 100% (reached the set maximum value).


### `%progress_percentage_{placeholder}%`

#### `{placeholder}` (**Required field**)
> **Requires**: A placeholder that returns a number OR a letter (A-Z). [Available placeholders] <br />
> **Example**: `%progress_percentage_{server_online}%`

The number that the percentage will be based on. For letters, it will be so `a` = 1, `z` = 26

#### `m:`
> **Requires**: `NUMBER` <br />
> **Default (Config option)**: `100` (`maximum_value`) <br />
> **Example**: `%progress_percentage_{server_online}_m:100%` <br />

Sets the maximum value (Goal) of the percentage. This affects the percentage itself.

#### `d:`
> **Requires**: `NUMBER` <br />
> **Default (Config option)**: `2` (`decimal`) <br />
> **Example**: `%progress_percentage_{server_online}_d:0%` <br />

Sets the amount of decimals that will be shown.
For example, with `d:3` if the percentage is `50` it will return `50.000`

# Download
You can download this expansion automatically using PAPI download commands:

> ```
> /papi ecloud download Progress
> /papi reload
> ```

Or you can download it manually from the [eCloud](https://api.extendedclip.com/expansions/progress/) and then put it in the `expansions` folder (Path: `/plugins/PlaceholderAPI/expansions/`)
